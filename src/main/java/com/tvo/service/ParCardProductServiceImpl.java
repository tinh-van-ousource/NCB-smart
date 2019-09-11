package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.FileStorageException;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.FileStorageProperties;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dao.ParCardProductDao;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParCardProductServiceImpl implements ParCardProductService {

    private final Path fileStorageLocation;

    @Autowired
    public ParCardProductServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Autowired
    ParCardProductDao parCardProductDao;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ParCardProductResDto> search(ParCardSearch searchModel, Pageable pageable) {
        final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<ParCardProductEntity> query = cb.createQuery(ParCardProductEntity.class);
        Object[] queryObjs = this.createRootPersist(cb, query, searchModel);
        query.select((Root<ParCardProductEntity>) queryObjs[0]);
        query.where((Predicate[]) queryObjs[1]);
        TypedQuery<ParCardProductEntity> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        final List<ParCardProductEntity> objects = typedQuery.getResultList();
        List<ParCardProductResDto> objectDtos = ModelMapperUtils.mapAll(objects, ParCardProductResDto.class);

        final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cbTotal.count(countQuery.from(ParCardProductEntity.class)));
        countQuery.where((Predicate[]) queryObjs[1]);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(objectDtos, pageable, total);
    }

    private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ParCardSearch resource) {
        final Root<ParCardProductEntity> rootPersist = query.from(ParCardProductEntity.class);
        final List<Predicate> predicates = new ArrayList<Predicate>(6);
        if (resource.getClasss() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getClasss().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("class_"), resource.getClasss())));
        }
        if (resource.getProduct() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getProduct().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("product"), resource.getProduct())));
        }

        if (resource.getPrdcode() != null
                && !org.apache.commons.lang3.StringUtils.isEmpty(resource.getPrdcode().trim())) {
            predicates.add(cb.and(cb.equal(rootPersist.<String>get("prdcode"), resource.getPrdcode())));
        }
        Object[] results = new Object[2];
        results[0] = rootPersist;
        results[1] = predicates.toArray(new Predicate[predicates.size()]);
        return results;
    }

    @Override
    public ParCardProductEntity findPrdcode(String prdcode) {
        ParCardProductEntity parCardProductEntity = parCardProductDao.findByPrdcode(prdcode);
        if (parCardProductEntity == null) {
            return new ParCardProductEntity();
        }
        return parCardProductEntity;
    }

    @Override
    public ParCardProductResDto edit(ParCardProductCreateReqDto request) {
        ParCardProductEntity parCardProductEntity = parCardProductDao.findByPrdcode(request.getPrdcode());
        if (!ObjectUtils.isEmpty(parCardProductEntity)) {
            ParCardProductEntity save = parCardProductDao.saveAndFlush(ModelMapperUtils.map(request, ParCardProductEntity.class));
            return ModelMapperUtils.map(save, ParCardProductResDto.class);
        }
        return null;
    }

    @Override
    public String delete(String prdCode) {
        if (!prdCode.isEmpty()) {
            ParCardProductEntity parCardProductEntity = parCardProductDao.findByPrdcode(prdCode);
            parCardProductEntity.setStatus(AppConstant.USER_STATUS_STRING_DEACTIVATED);
            parCardProductDao.saveAndFlush(parCardProductEntity);
            return AppConstant.SYSTEM_SUCCESS_CODE;
        }
        return AppConstant.SYSTEM_ERROR_CODE;
    }

    private String storeFile(MultipartFile file) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

        // Normalize file name
        String fileName = file.getOriginalFilename();
        fileName = currentUserName + "_" + now.format(formatter) + "_" + fileName;
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private UploadFileResponse uploadFile(MultipartFile file) {
        String fileName = storeFile(file);

        String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/")
                .path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileUploadUri, file.getContentType(), file.getSize());
    }

    @Transactional
    @Override
    public ParCardProductResDto create(MultipartFile multipartFiles, ParCardProductCreateReqDto request) {
        ParCardProductEntity findByPrdcode = parCardProductDao.findByPrdcode(request.getPrdcode());
        if (!ObjectUtils.isEmpty(findByPrdcode)) {
            return null;
        }

        UploadFileResponse uploadFileResponse = uploadFile(multipartFiles);
        request.setLinkUrl(uploadFileResponse.getFileUploadUri());
        request.setFileName(uploadFileResponse.getFileName());

        ParCardProductEntity data = ModelMapperUtils.map(request, ParCardProductEntity.class);
        data.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        ParCardProductEntity save = parCardProductDao.save(data);

        return ModelMapperUtils.map(save, ParCardProductResDto.class);
    }
}

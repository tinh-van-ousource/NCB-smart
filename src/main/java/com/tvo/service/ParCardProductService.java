/**
 * 
 */
package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.FileStorageException;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.FileStorageProperties;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dao.ParCardProductDao;
import com.tvo.dto.ParCardProductDto;
import com.tvo.model.ParCardProduct;
import com.tvo.request.PardCardProductCreate;
import com.tvo.response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ace
 *
 */
@Service
public class ParCardProductService {

	private final Path fileStorageLocation;

	@Autowired
	public ParCardProductService(FileStorageProperties fileStorageProperties) {
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

	@SuppressWarnings("unchecked")
	public Page<ParCardProductDto> search(ParCardSearch searchModel, Pageable pageable) {
		final CriteriaBuilder cb = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<ParCardProduct> query = cb.createQuery(ParCardProduct.class);
		Object[] queryObjs = this.createRootPersist(cb, query, searchModel);
		query.select((Root<ParCardProduct>) queryObjs[0]);
		query.where((Predicate[]) queryObjs[1]);
		TypedQuery<ParCardProduct> typedQuery = this.entityManager.createQuery(query);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());
		final List<ParCardProduct> objects = typedQuery.getResultList();
		List<ParCardProductDto> objectDtos = ModelMapperUtils.mapAll(objects, ParCardProductDto.class);

		final CriteriaBuilder cbTotal = this.entityManagerFactory.getCriteriaBuilder();
		final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		countQuery.select(cbTotal.count(countQuery.from(ParCardProduct.class)));
		countQuery.where((Predicate[]) queryObjs[1]);
		Long total = entityManager.createQuery(countQuery).getSingleResult();
		return new PageImpl<>(objectDtos, pageable, total);
	};

	private Object[] createRootPersist(CriteriaBuilder cb, CriteriaQuery<?> query, ParCardSearch resource) {
		final Root<ParCardProduct> rootPersist = query.from(ParCardProduct.class);
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

	public ParCardProduct findPrdcode(String prdcode) {
		ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(prdcode);
		if (parCardProduct == null) {
			return new ParCardProduct();
		}
		return parCardProduct;
	}

	@Transactional(readOnly = false)
	public ParCardProductDto create(PardCardProductCreate request) {
		ParCardProduct findByPrdcode = parCardProductDao.findByPrdcode(request.getPrdcode());
		if (!ObjectUtils.isEmpty(findByPrdcode)) {
			return null;
		}
		ParCardProduct data = ModelMapperUtils.map(request, ParCardProduct.class);
		ParCardProduct save = parCardProductDao.save(data);
		return ModelMapperUtils.map(save, ParCardProductDto.class);
	}

	public ParCardProductDto edit(PardCardProductCreate request) {
		ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(request.getPrdcode());
		if (!ObjectUtils.isEmpty(parCardProduct)) {
			ParCardProduct save = parCardProductDao.saveAndFlush(ModelMapperUtils.map(request, ParCardProduct.class));
			return ModelMapperUtils.map(save, ParCardProductDto.class);
		}
		return null;
	}

	public String delete(String prdCode) {
		if (!prdCode.isEmpty()) {
			ParCardProduct parCardProduct = parCardProductDao.findByPrdcode(prdCode);
			parCardProduct.setStatus(AppConstant.USER_STATUS_STRING_DEACTIVATED);
			parCardProductDao.saveAndFlush(parCardProduct);
			return AppConstant.SYSTEM_SUCCESS_CODE;
		}
		return AppConstant.SYSTEM_ERROR_CODE;
	}

	public String storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = file.getOriginalFilename().toString();

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
	
	public UploadFileResponse uploadFile(MultipartFile file) {
		String fileName = storeFile(file);

		String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileUploadUri, file.getContentType(), file.getSize());
	}
}

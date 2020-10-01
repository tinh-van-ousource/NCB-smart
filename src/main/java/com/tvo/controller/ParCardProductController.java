package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.controllerDto.ParCardSearchReqDto;
import com.tvo.dto.CompanyResDto;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.request.ParCardProductUpdateReqDto;
import com.tvo.response.ResponeData;
import com.tvo.service.ParCardProductService;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "par-card")
public class ParCardProductController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
    @Autowired
    private ParCardProductService parCardProductService;

    @GetMapping(value = "search")
    public ResponeData<Page<ParCardProductResDto>> search(@ModelAttribute ParCardSearchReqDto searchModel,
                                                          @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<ParCardProductResDto> dts = parCardProductService.search(searchModel, pageable);
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Tìm kiếm Sản phẩm thẻ");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
    }

    @GetMapping(value = "detail")
    public ResponeData<ParCardProductResDto> detail(@RequestParam String prdcode) {
        ParCardProductResDto parCardProductResDto = parCardProductService.findByPrdcode(prdcode);

        if (parCardProductResDto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Chi tiết Sản phẩm thẻ");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProductResDto);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam String prdCode) {
        boolean result = parCardProductService.delete(prdCode);
        if (result) {
            try {
    			ip = InetAddress.getLocalHost();
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
            		"\n Account :"+Flag.userFlag.getUserName().toString()+
            		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
            		" \n Địa chỉ IP đăng nhập : " + ip+
            		" \n Hostname : " + hostname +
            		" \n Xóa Sản phẩm thẻ");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }

    @PutMapping(value = "update")
    public ResponeData<ParCardProductResDto> update(@RequestBody ParCardProductCreateReqDto parCardProductUpdateReqDto) {
        ParCardProductResDto parCardProduct = parCardProductService.update(parCardProductUpdateReqDto);
        if (parCardProduct != null) {
            try {
    			ip = InetAddress.getLocalHost();
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
            		"\n Account :"+Flag.userFlag.getUserName().toString()+
            		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
            		" \n Địa chỉ IP đăng nhập : " + ip+
            		" \n Hostname : " + hostname +
            		" \n Cập nhật thông tin Sản phẩm thẻ");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduct);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @PostMapping(value = "create")
    public ResponeData<ParCardProductResDto> create(@RequestBody ParCardProductCreateReqDto parCardProductCreateReqDto) {
    	ParCardProductResDto resDto = parCardProductService.create(parCardProductCreateReqDto);
          if (resDto == null) {
              return new ResponeData<>(AppConstant.COMPANY_EXISTED_CODE, AppConstant.COMPANY_EXISTED_MESSAGE, null);
          }
          try {
  			ip = InetAddress.getLocalHost();
  		} catch (UnknownHostException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
          hostname = ip.getHostName();
          logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
          		"\n Account :"+Flag.userFlag.getUserName().toString()+
          		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
          		" \n Địa chỉ IP đăng nhập : " + ip+
          		" \n Hostname : " + hostname +
          		" \n Tạo mới Sản phẩm thẻ");
          return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
//      
//        try {
//            ParCardProductResDto parCardProduct = parCardProductService.create(parCardProductCreateReqDto);
//            if (parCardProduct != null) {
//                return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduct);
//            }
//            return new ResponeData<>(AppConstant.PAR_CARD_EXISTED_CODE, AppConstant.PAR_CARD_EXISTED_MESSAGE, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
//        }
    }

}

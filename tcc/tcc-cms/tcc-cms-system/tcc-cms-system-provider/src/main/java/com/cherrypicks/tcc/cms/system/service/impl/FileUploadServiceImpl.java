package com.cherrypicks.tcc.cms.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.system.service.FileUploadService;
import com.cherrypicks.tcc.cms.system.vo.FileUploadVO;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.FileUtil;
import com.cherrypicks.tcc.util.I18nUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class FileUploadServiceImpl implements FileUploadService {

	@Value("${upload.path}")
	private String uploadPath;
	
	@Reference(version="1.0")
	private MerchantConfigService merchantConfigService;

	@Override
	public String upload(MultipartFile file, String lang) throws Exception {

		if (null == file || file.isEmpty()) {
			throw new IllegalArgumentException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}

		boolean checkFlag = FileUtil.checkImageFileSuffix(file.getOriginalFilename());
		if (!checkFlag) {
			throw new FileUploadException(I18nUtil.getMessage(CmsCodeStatus.FILE_UPLOAD_EXCEPTION, null, lang));
		}

		return FileUtil.uploadFile(file.getInputStream(), file.getOriginalFilename(), uploadPath);
	}

	@Override
	public FileUploadVO uploadBase64File(final String base64ImageFile,final Long merchantId,final Integer type,final String lang) throws Exception {

		String uuid = null;
		FileOutputStream fos = null;
		int indexImage = base64ImageFile.indexOf("base64");
		String realImage = base64ImageFile.substring(indexImage + 7, base64ImageFile.length());
		int indexSlash = base64ImageFile.indexOf("/");
		int indexColon = base64ImageFile.indexOf(";");
		String imageFormat = base64ImageFile.substring(indexSlash + 1, indexColon);
		String fileName = StringUtils.EMPTY;
		FileUploadVO fileInfo = new FileUploadVO();
		try {
			
			// 对字节数组字符串进行Base64解码并生成图片
			uuid = UUID.randomUUID().toString().replace("-", "");
			byte[] decoded = Base64.decodeBase64(realImage);

			StringBuilder fileUploadPath = new StringBuilder(uploadPath).append(File.separator).append(merchantId).append(File.separator);

			StringBuilder merchantFilePath = new StringBuilder(uploadPath).append(File.separator).append(merchantId).append(File.separator);
			
			File merchantFolder = new File(merchantFilePath.toString());
			if(!merchantFolder.exists()){
				merchantFolder.mkdirs();
			}
			
			fileName = new StringBuilder().append(uuid).append(".").append(imageFormat).toString();
			if (type.intValue() == FileUploadVO.FileUploadType.CAMPAIGN.getType()) {
				fileName = merchantId + File.separator + FileUploadVO.FileUploadType.CAMPAIGN.getPath() + File.separator + fileName;
				merchantFilePath.append(FileUploadVO.FileUploadType.CAMPAIGN.getPath());
				fileUploadPath.append(FileUploadVO.FileUploadType.CAMPAIGN.getPath());

			} else if (type.intValue() == FileUploadVO.FileUploadType.MERCHANT.getType()) {
				fileName = merchantId + File.separator + FileUploadVO.FileUploadType.MERCHANT.getPath() + File.separator + fileName;
				merchantFilePath.append(FileUploadVO.FileUploadType.MERCHANT.getPath());
				fileUploadPath.append(FileUploadVO.FileUploadType.MERCHANT.getPath());

			} else if (type.intValue() == FileUploadVO.FileUploadType.STAMP.getType()) {
				fileName = merchantId + File.separator + FileUploadVO.FileUploadType.STAMP.getPath() + File.separator + fileName;
				merchantFilePath.append(FileUploadVO.FileUploadType.STAMP.getPath());
				fileUploadPath.append(FileUploadVO.FileUploadType.STAMP.getPath());

			} else if (type.intValue() == FileUploadVO.FileUploadType.PROMOTION.getType()) {
				fileName = merchantId + File.separator + FileUploadVO.FileUploadType.PROMOTION.getPath() + File.separator + fileName;
				merchantFilePath.append(FileUploadVO.FileUploadType.PROMOTION.getPath());
				fileUploadPath.append(FileUploadVO.FileUploadType.PROMOTION.getPath());
				
			}else{
				fileName = merchantId + File.separator + FileUploadVO.FileUploadType.DEFAULT_TYPE.getPath() + File.separator + fileName;
				merchantFilePath.append(FileUploadVO.FileUploadType.DEFAULT_TYPE.getPath());
				fileUploadPath.append(FileUploadVO.FileUploadType.DEFAULT_TYPE.getPath());
			
			}

			File uploadFolder = new File(fileUploadPath.toString());
			if (!uploadFolder.exists()) {
				uploadFolder.mkdirs();
			}


			merchantFilePath.append(File.separator).append(uuid).append(".").append(imageFormat).toString();
			fos = new FileOutputStream(merchantFilePath.toString());
			fos.write(decoded);
			fos.flush();
			fos.close();

			MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(merchantId);
			
			fileInfo.setFullName(merchantConfig.getImgDomain()+fileName);
			fileInfo.setFileName(fileName);
		} catch (Exception e) {
			throw new FileUploadException(e.getMessage());
		}
		return fileInfo;
	}

}

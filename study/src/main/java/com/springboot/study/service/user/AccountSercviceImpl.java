package com.springboot.study.service.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountSercviceImpl implements AccountService {
	

	@Value("${file.path}") // 생성될때 만들어지는 setter
	private String filePath;

	private final UserRepository userRepository;
	
	@Override
	public boolean updateProfileImg(MultipartFile file, PrincipalDetails principalDetails) {
		if (file != null) {
			String originalFileName = file.getOriginalFilename();
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFileName;
			Path uploadPath = Paths.get(filePath, "profile/" + tempFileName);

			File f = new File(filePath + "profile");
			if (!f.exists()) {// 해당경로가 존재하면 true 존재하지 않으면 경로를 만들어줘야되기 때문에 not을 붙임
				f.mkdirs();
			}
			try {
				Files.write(uploadPath, file.getBytes());
				User user = principalDetails.getUser();
				user.setProfile_img_url(tempFileName);
				return userRepository.updateProfileImg(user)>0;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return false;
	}
}

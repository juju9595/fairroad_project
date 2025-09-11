package web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    // 프로젝트 최상위 디렉토리 경로찾기
    private String baseDir = System.getProperty("user.dir");
    // 서버의 폴더로 업로드 경로 지정
    private String uploadPath = baseDir + "/src/main/resources/static/upload";

    //파일 업로드
    public String fileUpload(MultipartFile multipartFile){
        System.out.println("FileService.fileUpload");
        System.out.println("multipartFile = " + multipartFile);

        String uuid = UUID.randomUUID().toString(); //파일명 UUID
        String fimg = uuid +"_"+multipartFile.getOriginalFilename().replaceAll("_","-");//파일명 ("기본문자"+"새로운문자")
        //String uploadFilePath = uploadPath + fimg; // 업로드 경로와 파일명 합치기 //수정전
        String uploadFilePath = uploadPath +"/"+ fimg; // 업로드 경로와 파일명 합치기

        File pathFile = new File(uploadPath); // 업로드한 경로가 upload 폴더가 존재 하지않으면 폴더 생성
        System.out.println("pathFile"+pathFile);

        if(!pathFile.exists()){
            pathFile.mkdir();
        }//if end
        // File uploadFile = new File(uploadPath, fimg); //수정전
        File uploadFile = new File(uploadFilePath); //업로드 할 경로를 file 클래스 생성

        System.out.println("uploadFile"+uploadFile);

        try{
            multipartFile.transferTo(uploadFile); // multipartFile(업로드된 파일)
        } catch (IOException e) {System.out.println(e); return null;}//catch end
        return fimg;
    }//func end
}//class end

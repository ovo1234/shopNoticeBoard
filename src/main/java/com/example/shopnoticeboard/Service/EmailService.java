package com.example.shopnoticeboard.Service;

import com.example.shopnoticeboard.Dto.user.Email.EmailSaveRequest;
import com.example.shopnoticeboard.Entity.EmailAuth;
import com.example.shopnoticeboard.Error.ErrorException;
import com.example.shopnoticeboard.Repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Random;

import static com.example.shopnoticeboard.Error.ErrorCode.ACCESS_DENIED_EXCEPTION;
import static com.example.shopnoticeboard.Error.ErrorCode.NOT_ALLOW_WRITE_EXCEPTION;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailAuthRepository emailAuthRepository;
    private String randomCode;

    // 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            if (random.nextBoolean()) { // 랜덤으로 true, false 리턴
                key.append((char)((int)(random.nextInt(26)) + 97)); // 소문자
            } else {
                key.append(random.nextInt(10)); // 숫자
            }
        }
        randomCode = key.toString();
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException {
        createCode(); //인증 코드 생성
        String setFrom = "shop"; //(보내는 사람)
        String title = "shop 비밀번호 2차인증"; //제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<div style=\"margin:100px;\">";
        msg += "<h1 style = \"text-align: center;\"> Shop 비밀번호 인증 메일</h1> <br>";
        msg += "<p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>\n<p> 인증 번호 : ";
        msg += randomCode;
        msg += "</p>\n<div>\n<br/><br/><br/>";
        msg += "<p style = \"text-align : right;\">shop</p>";
        msg += "</div>\n<br />\n</div>";

        System.out.println(email +" : "+msg);

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(msg, "utf-8", "html");

        return message;
    }

    // 메일 보내기
    public void sendEmail(String toEmail) throws MessagingException {
        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);
    }

    // 데베에 저장
    public void saveDB(String email) throws MessagingException {
        if(emailAuthRepository.existsByEmail(email)){
            throw new ErrorException("이미 인증 메일을 보냈습니다.", NOT_ALLOW_WRITE_EXCEPTION);
        }
        this.sendEmail(email);
        EmailSaveRequest emailSaveRequest = new EmailSaveRequest();
        emailSaveRequest.setEmail(email);
        emailSaveRequest.setRandomCode(randomCode);
        emailAuthRepository.save(emailSaveRequest.toEntity());
    }

    public void verifyEmail(String email, String user_randCode){
        EmailAuth emailAuth = emailAuthRepository.findByEmail(email).orElseThrow();

        // 현재 시각 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();

        if(currentDateTime.isAfter(emailAuth.getCreatedDate().plusMinutes(10))){
            emailAuthRepository.deleteByEmail(email);
            throw new ErrorException("인증 가능 시간이 지났습니다. 다시 한번 시도하세요.", NOT_ALLOW_WRITE_EXCEPTION);
        }

        if(!user_randCode.equals(emailAuth.getRandomCode())){
            throw new ErrorException("2차 인증 암호가 틀렸습니다. 다시 한번 시도하세요..", NOT_ALLOW_WRITE_EXCEPTION);
        }
    }

}

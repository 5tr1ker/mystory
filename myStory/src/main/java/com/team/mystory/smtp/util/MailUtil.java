package com.team.mystory.smtp.util;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public boolean sendMail(String ToEmail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject("이편지는..."); // 제목
            helper.setTo(ToEmail); // 받는사람
            helper.setFrom("sangjinb609@gmail.com");

            String BODY = String.join(
                    System.getProperty("line.separator"),
                    "<head>\n" +
                            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                            "        <title>행운의 편지</title>\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                            "    </head>\n" +
                            "\n" +
                            "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"550px\" style=\"border: 1px solid #d7d7d7; border-radius: 20px; text-align: center; font-family:'Malgun Gothic', '맑은 고딕'; letter-spacing: -0.04em; color: #333333;\">\n" +
                            "        <tr>\n" +
                            "            <td style=\"width: 40px;\"></td>\n" +
                            "            <td>\n" +
                            "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"470px;\">\n" +
                            "                    <tr><td style=\"height: 60px;\"></td></tr>\n" +
                            "                    <tr><td style=\"height: 18px;\"></td></tr>\n" +
                            "                    <tr><td><span style=\"font-size: 32px; font-weight: bold;\">행운의 편지</span></td></tr>\n" +
                            "                    <tr><td style=\"height: 34px;\"></td></tr>\n" +
                            "                    <tr><td style=\"height: 1px; background: #eaeaea;\"></td></tr>\n" +
                            "                    <tr><td style=\"height: 30px;\"></td></tr>\n" +
                            "                    <tr>\n" +
                            "                        <td>\n" +
                            "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"340px;\" style=\"margin: 0 auto;\">\n" +
                            "                                <tr>\n" +
                            "                                    <td>\n" +
                            "                                        <span style=\"font-size: 14px;\">\n" +
                            "                                            이 편지는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고 지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다. 이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다. 복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다.\n" +
                            "                        \"영국에서 HGXWCH이라는 사람은 1930년에 이 편지를 받았습니다. 그는 비서에게 복사해서 보내라고 했습니다. 며칠 뒤에 복권이 당첨되어 20억을 받았습니다. 어떤 이는 이 편지를 받았으나 96시간 이내 자신의 손에서 떠나야 한다는 사실을 잊었습니다. 그는 곧 사직되었습니다. 나중에야 이 사실을 알고 7통의 편지를 보냈는데 다시 좋은 직장을 얻었습니다. 미국의 케네디 대통령은 이 편지를 받았지만 그냥 버렸습니다. 결국 9일 후 그는 암살당했습니다. 기억해 주세요. 이 편지를 보내면 7년의 행운이 있을 것이고 그렇지 않으면 3년의 불행이 있을 것입니다. 그리고 이 편지를 버리거나 낙서를 해서는 절대로 안됩니다. 7통입니다. 이 편지를 받은 사람은 행운이 깃들것입니다. 힘들겠지만 좋은게 좋다고 생각하세요. 7년의 행운을 빌면서..." +
                            "                                        </span>\n" +
                            "                                    </td>\n" +
                            "                                </tr>\n<tr><td style=\"height: 30px;\"></td></tr>" +
                            "                            </table>\n" +
                            "                        </td>\n" +
                            "                    \n" +
                            "                </table>\n" +
                            "            </td>\n" +
                            "            <td style=\"width: 40px;\"></td>\n" +
                            "        </tr>\n" +
                            "    </table>",
                    "<p></p>"
            );
            helper.setText(BODY, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
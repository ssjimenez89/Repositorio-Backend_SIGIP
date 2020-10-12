package cu.uci.cegel.base.integration.messaging.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class Correo {

    private JavaMailSender mailSender;
    private CorreoContentBuilder correoContentBuilder;
    private Environment env;

    @Autowired
    public Correo(JavaMailSender mailSender, CorreoContentBuilder correoContentBuilder, Environment env) {
        this.mailSender = mailSender;
        this.correoContentBuilder = correoContentBuilder;
        this.env = env;
    }

    public void sendEmail(String to, String subject, String notification,  String nombreArchivo, File file, String from) {
        String[] array = {to};
        envio(array, subject, notification, nombreArchivo, file, from);
    }

    public void sendEmail(List<String> to, String subject, String notification, String nombreArchivo, File file, String from) {
        String[] array = new String[to.size()];
        envio(to.toArray(array), subject, notification, nombreArchivo, file, from);
    }

    public void sendEmail(List<String> to, String subject, String notification, String nombreArchivo, File file) {
        String[] array = new String[to.size()];
        envio(to.toArray(array), subject, notification, nombreArchivo, file, null);
    }

    public void sendEmail(List<String> to, String subject, String notification) {
        String[] array = new String[to.size()];
        envio(to.toArray(array), subject, notification);
    }

    private void envio(String[] to, String subject, String notification) {
        MimeMessagePreparator message = mimeMessage -> {

            MimeMessageHelper message1 = new MimeMessageHelper(mimeMessage);
            message1.setTo(to);
            message1.setFrom(env.getProperty("spring.mail.username"));
            message1.setSubject(subject);
            String content = correoContentBuilder.build(notification);
            message1.setText(content, true);
        };

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar el email.");
        }
    }

    private void envio(String[] to, String subject, String notification, String nombreArchivo, File file, String from) {
        MimeMessagePreparator message = mimeMessage -> {

            MimeMessageHelper message1 = new MimeMessageHelper(mimeMessage,  true, "UTF-8");
            message1.setTo(to);
            message1.setFrom(from != null ? from : env.getProperty("spring.mail.username"));
            message1.setSubject(subject);
            String content = correoContentBuilder.build(notification);
            message1.setText(content, true);
            message1.addAttachment(nombreArchivo, file);
        };

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enviar el email.");
        }
    }
}

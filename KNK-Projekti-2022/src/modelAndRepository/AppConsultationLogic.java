package modelAndRepository;


import java.sql.Date;
import java.time.LocalDate;

public class AppConsultationLogic {
    public static LocalDate localDate = LocalDate.of(2022, 05, 27);
    public static Date date =  Date.valueOf(localDate);
    public static Patient person = new Patient("name","last name",date);
}

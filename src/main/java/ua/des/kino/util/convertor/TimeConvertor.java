package ua.des.kino.util.convertor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeConvertor {

    private final static Logger logger = LoggerFactory.getLogger(TimeConvertor.class.getName());

    public List<Timestamp> convert(String[] strings){
        List<Timestamp> sqlDate = new ArrayList<>();

        for(String str: strings) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(str);
                sqlDate.add(new java.sql.Timestamp(parsedDate.getTime()));
            } catch (Exception e) {
                logger.error("Cannot convert Date to Timestamp");
                e.printStackTrace();
            }
        }
        return sqlDate;
    }
}

package managers;

import beans.ClassBean;
import beans.ScheduledFeedbackBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sapan
 */
public class FeedbackManager {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    public FeedbackManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        LogManager.log("Feedback Manager initiated!");
    }
    

}

//    public List<ClassBean> findAllFeedbackEligibleClass() {
//        //Those classes which can give feeback now 
//        List<ClassBean> list = new ArrayList();
//        int currentTime = UtilsManager.getCurrentTimestampInSeconds();
//        List<ScheduledFeedbackBean> findAllEligibleSFB = new ScheduledFeedbackBean().findAllFeedbackEligibleClass(currentTime);
//        for (ScheduledFeedbackBean sfb : findAllEligibleSFB) {
//            list.add(sfb.getClassId());
//        }
//        return list;
//    }

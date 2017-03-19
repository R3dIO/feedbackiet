package managers;

import beans.ClassBean;
import beans.CourseBean;
import beans.DepartmentBean;
import beans.ScheduledFeedbackBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sapan
 */
public class JsonApiManager {

    HttpServletRequest request;
    HttpServletResponse response;

    public JsonApiManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    private String convertBeanToJson(List list) {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gb.create();
        return (gson.toJson(list));
    }

    public String findAllCourses() {
        CourseBean helperCB = new CourseBean();
        List<CourseBean> cbList = helperCB.findAll();
        return convertBeanToJson(cbList);
    }

    public String findDepartmentByCourseId(Long courseId) {
        CourseBean cb = new CourseBean(courseId);
        DepartmentBean helperDB = new DepartmentBean();
        helperDB.setCourseId(cb);
        List<DepartmentBean> dbList = helperDB.findByCourseId();
        return convertBeanToJson(dbList);
    }

    public String findClassByDepartmentId(Long departmentId) {
        DepartmentBean db = new DepartmentBean(departmentId);
        ClassBean cb = new ClassBean();
        cb.setDepartmentId(db);
        List<ClassBean> cbList = cb.findClassByDepartmentId();
        return convertBeanToJson(cbList);
    }

    public String findClassByDepartmentIdNotInScheduledFeedback(Long departmentId) {
        DepartmentBean db = new DepartmentBean(departmentId);
        ClassBean cb = new ClassBean();
        cb.setDepartmentId(db);
        List<ClassBean> cbList = cb.findClassByDepartmentIdNotInScheduledFeedback();
        return convertBeanToJson(cbList);
    }

    public String findClassByDepartmentIdInScheduledFeedback(Long departmentId) {
        DepartmentBean db = new DepartmentBean(departmentId);
        ClassBean cb = new ClassBean();
        cb.setDepartmentId(db);
        List<ClassBean> cbList = cb.findClassByDepartmentIdInScheduledFeedback();
        return convertBeanToJson(cbList);
    }

    public String scheduleClassFeedback(ScheduledFeedbackBean sfb) {
        Gson gson = new Gson();
        boolean success = sfb.scheduleClassFeedback();
        JsonObject jo = new JsonObject();
        jo.addProperty("success", success);
        if (success) {
            jo.addProperty("message", "Feedback successfully scheduled for class " + sfb.getClassId().getClassCodeById());
        } else {
            jo.addProperty("message", "Feedback scheduling failed for class " + sfb.getClassId().getClassCodeById());
        }
        return gson.toJson(jo);
    }

    public String deleteFeedbackScheduleOfClass(ScheduledFeedbackBean sfb) {
        Gson gson = new Gson();
        boolean success = sfb.deleteFeedbackScheduleOfClass();
        JsonObject jo = new JsonObject();
        jo.addProperty("success", success);
        if (success) {
            jo.addProperty("message", "Feedback schedule removed for class " + sfb.getClassId().getClassCodeById());
        } else {
            jo.addProperty("message", "Could not remove feedback schedule for class " + sfb.getClassId().getClassCodeById());
        }
        return gson.toJson(jo);
    }

    public String findAllFeedbackEligibleClass() {
        Gson gson = new Gson();
        //Those classes which can give feeback now 
        List<ClassBean> list = new ArrayList();
        int currentTime = UtilsManager.getCurrentTimestampInSeconds();
        List<ScheduledFeedbackBean> findAllEligibleSFB = new ScheduledFeedbackBean().findAllFeedbackEligibleClass(currentTime);
        for (ScheduledFeedbackBean sfb : findAllEligibleSFB) {
            list.add(sfb.getClassId());
        }
        return convertBeanToJson(list);
    }
}

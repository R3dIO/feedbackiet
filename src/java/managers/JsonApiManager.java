package managers;

import beans.ClassBean;
import beans.CourseBean;
import beans.DepartmentBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private String convertToJson(List list) {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gb.create();
        return (gson.toJson(list));
    }

    public String findAllCourses() {
        CourseBean helperCB = new CourseBean();
        List<CourseBean> cbList = helperCB.findAll();
        return convertToJson(cbList);
    }

    public String findDepartmentByCourseId(Long courseId) {
        CourseBean cb = new CourseBean(courseId);
        DepartmentBean helperDB = new DepartmentBean();
        helperDB.setCourseId(cb);
        List<DepartmentBean> dbList = helperDB.findByCourseId();
        return convertToJson(dbList);
    }

    public String findClassByDepartmentId(Long departmentId) {
        DepartmentBean db = new DepartmentBean(departmentId);
        ClassBean cb = new ClassBean();
        cb.setDepartmentId(db);
        List<ClassBean> cbList = cb.findClassByDepartmentId();
        return convertToJson(cbList);
    }
}

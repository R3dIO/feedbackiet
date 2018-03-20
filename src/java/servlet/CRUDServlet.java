package servlet;

import beans.ClassBean;
import beans.CourseBean;
import beans.CsfBean;
import beans.DepartmentBean;
import beans.FacultyBean;
import beans.SubjectBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.SessionManager;
import managers.UtilsManager;

/**
 * @author Sapan
 */
public class CRUDServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String purpose = request.getParameter("purpose");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (purpose.equalsIgnoreCase("addCourse")) {
            addCourse(request, response);
        } else if (purpose.equalsIgnoreCase("editCourse")) {
            editCourse(request, response);
        } else if (purpose.equalsIgnoreCase("addDepartment")) {
            addDepartment(request, response);
        } else if (purpose.equalsIgnoreCase("editDepartment")) {
            editDepartment(request, response);
        } else if (purpose.equalsIgnoreCase("deleteDepartmentById")) {
            deleteDepartment(request, response);
        } else if (purpose.equalsIgnoreCase("addClass")) {
            // NOTE: if section in AddClass or editClass function is "" then make it null ;
            addClass(request, response);
        } else if (purpose.equalsIgnoreCase("editClass")) {
            // NOTE: if section in AddClass or editClass function is "" then make it null ;
            editClass(request, response);
        } else if (purpose.equalsIgnoreCase("deleteClassById")) {
            deleteClass(request, response);
        } else if (purpose.equalsIgnoreCase("addSubject")) {
            addSubject(request, response);
        } else if (purpose.equalsIgnoreCase("editSubject")) {
            editSubject(request, response);
        } else if (purpose.equalsIgnoreCase("addFaculty")) {
            addFaculty(request, response);
        } else if (purpose.equalsIgnoreCase("editFaculty")) {
            editFaculty(request, response);
        } else if (purpose.equalsIgnoreCase("deleteFacultyById")) {
            deleteFaculty(request, response);
        } else if (purpose.equalsIgnoreCase("editSchema")) {
            editSchema(request, response);
        } else if (purpose.equalsIgnoreCase("deleteSchemaByClassIdAndSubjectId")) {
            //Delete schema of a class using schema_id i.e. delete an entry from schema_table
            deleteSchemaByClassIdAndSubjectId(request, response);
        } else if (purpose.equalsIgnoreCase("editCSF")) {
            editCSF(request, response);
        } else if (purpose.equalsIgnoreCase("deleteCSFById")) {
            deleteCSFById(request, response);
        } else if (purpose.equalsIgnoreCase("changeAdminUsernamePassword")) {
            changeAdminUsernamePassword(request, response);
        } else {

        }

    }

    @Override
    public String getServletInfo() {
        return "This servlet is for create retrieve update delete purpose of beans";
    }// </editor-fold>

    private void addCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String courseCode, courseName;
        int duration;
        courseCode = req.getParameter("course_code");
        courseName = req.getParameter("course_name");
        duration = Integer.parseInt(req.getParameter("duration"));
        CourseBean cb = new CourseBean();
        cb.setCourseCode(courseCode);
        cb.setCourseName(courseName);
        cb.setDuration(duration);
        if (cb.persist(cb)) {
            resp.sendRedirect("admin/add/CourseAddEdit.jsp?message=Successfully added the course!&result=success");
        } else {
            resp.sendRedirect("admin/add/CourseAddEdit.jsp?message=Failed to add the course!&result=fail");
        }
    }

    private void editCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String courseCode, courseName;
        int duration;
        long courseId;
        courseCode = req.getParameter("course_code");
        courseName = req.getParameter("course_name");
        courseId = Long.parseLong(req.getParameter("course_id"));
        duration = Integer.parseInt(req.getParameter("duration"));
        CourseBean cb = new CourseBean(courseId);
        cb.setCourseCode(courseCode);
        cb.setCourseName(courseName);
        cb.setDuration(duration);
        if (cb.merge(cb)) {
            resp.sendRedirect("admin/add/CourseAddEdit.jsp?message=Successfully added the course!&result=success");
        } else {
            resp.sendRedirect("admin/add/CourseAddEdit.jsp?message=Failed to add the course!&result=fail");
        }
    }

    private void addDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String departmentCode, departmentName;
        long facultyId, courseId;
        departmentCode = req.getParameter("department_code");
        departmentName = req.getParameter("department_name");
        facultyId = Long.parseLong(req.getParameter("faculty_id"));
        courseId = Long.parseLong(req.getParameter("course_id"));
        DepartmentBean db = new DepartmentBean();
        db.setDepartmentCode(departmentCode);
        db.setDepartmentName(departmentName);
        db.setCourseId(new CourseBean(courseId));
        db.setFacultyId(new FacultyBean(facultyId));
        if (db.persist(db)) {
            resp.sendRedirect("admin/add/DepartmentAddEdit.jsp?message=Successfully added the department!&result=success");
        } else {
            resp.sendRedirect("admin/add/DepartmentAddEdit.jsp?message=Failed to add the department!&result=fail");
        }
    }

    private void editDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String departmentCode, departmentName;
        long departmentId, facultyId, courseId;
        departmentCode = req.getParameter("department_code");
        departmentName = req.getParameter("department_name");
        facultyId = Long.parseLong(req.getParameter("faculty_id"));
        courseId = Long.parseLong(req.getParameter("course_id"));
        departmentId = Long.parseLong(req.getParameter("department_id"));
        DepartmentBean db = new DepartmentBean(departmentId);
        db.setDepartmentCode(departmentCode);
        db.setDepartmentName(departmentName);
        db.setCourseId(new CourseBean(courseId));
        db.setFacultyId(new FacultyBean(facultyId));
        if (db.merge(db)) {
            resp.sendRedirect("admin/add/DepartmentAddEdit.jsp?message=Successfully added the department!&result=success");
        } else {
            resp.sendRedirect("admin/add/DepartmentAddEdit.jsp?message=Failed to add the department!&result=fail");
        }
    }

    private void addFaculty(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title, firstName, lastName, designation, email, phone;
        long departmentId;
        title = req.getParameter("title");
        firstName = req.getParameter("first_name");
        lastName = req.getParameter("last_name");
        designation = req.getParameter("designation");
        email = req.getParameter("email");
        phone = req.getParameter("phone");
        departmentId = Long.parseLong(req.getParameter("department_id"));
        FacultyBean fb = new FacultyBean();
        fb.setTitle(title);
        fb.setFirstName(firstName);
        fb.setLastName(lastName);
        fb.setDesignation(designation);
        fb.setPhone(phone);
        fb.setEmail(email);
        fb.setDepartmentId(new DepartmentBean(departmentId));
        if (fb.persist(fb)) {
            resp.sendRedirect("admin/add/FacultyAddEdit.jsp?message=Successfully added the faculty!&result=success");
        } else {
            resp.sendRedirect("admin/add/FacultyAddEdit.jsp?message=Failed to add the faculty!&result=fail");
        }
    }

    private void editFaculty(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title, firstName, lastName, designation, email, phone;
        long departmentId, facultyId;
        title = req.getParameter("title");
        firstName = req.getParameter("first_name");
        lastName = req.getParameter("last_name");
        designation = req.getParameter("designation");
        email = req.getParameter("email");
        phone = req.getParameter("phone");
        departmentId = Long.parseLong(req.getParameter("department_id"));
        facultyId = Long.parseLong(req.getParameter("faculty_id"));
        FacultyBean fb = new FacultyBean(facultyId);
        fb.setTitle(title);
        fb.setFirstName(firstName);
        fb.setLastName(lastName);
        fb.setDesignation(designation);
        fb.setPhone(phone);
        fb.setEmail(email);
        fb.setDepartmentId(new DepartmentBean(departmentId));
        if (fb.merge(fb)) {
            resp.sendRedirect("admin/add/FacultyAddEdit.jsp?message=Successfully edited the faculty!&result=success");
        } else {
            resp.sendRedirect("admin/add/FacultyAddEdit.jsp?message=Failed to edit the faculty!&result=fail");
        }
    }

    private void addClass(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String section;
        long facultyId, departmentId;
        int year;
        departmentId = Long.parseLong(req.getParameter("department_id"));
        year = Integer.parseInt(req.getParameter("year"));
        section = req.getParameter("section");
        if (section != null && section.trim().equalsIgnoreCase("")) {
            section = null;
        }
        facultyId = Long.parseLong(req.getParameter("faculty_id"));
        ClassBean cb = new ClassBean();
        cb.setDepartmentId(new DepartmentBean(departmentId));
        cb.setYear(year);
        cb.setSection(section);
        cb.setFacultyId(new FacultyBean(facultyId));
        if (cb.persist(cb)) {
            resp.sendRedirect("admin/add/ClassAddEdit.jsp?message=Successfully added the class!&result=success");
        } else {
            resp.sendRedirect("admin/add/ClassAddEdit.jsp?message=Failed to add the class!&result=fail");
        }
    }

    private void editClass(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String section;
        long facultyId, departmentId, classId;
        int year;
        classId = Long.parseLong(req.getParameter("class_id"));
        departmentId = Long.parseLong(req.getParameter("department_id"));
        year = Integer.parseInt(req.getParameter("year"));
        section = req.getParameter("section");
        if (section != null && section.trim().equalsIgnoreCase("")) {
            section = null;
        }
        facultyId = Long.parseLong(req.getParameter("faculty_id"));
        ClassBean cb = new ClassBean(classId);
        cb.setDepartmentId(new DepartmentBean(departmentId));
        cb.setYear(year);
        cb.setSection(section);
        cb.setFacultyId(new FacultyBean(facultyId));
        if (cb.merge(cb)) {
            resp.sendRedirect("admin/add/ClassAddEdit.jsp?message=Successfully added the class!&result=success");
        } else {
            resp.sendRedirect("admin/add/ClassAddEdit.jsp?message=Failed to add the class!&result=fail");
        }
    }

    private void deleteClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long classId = Long.parseLong(request.getParameter("class_id"));
        ClassBean cb = new ClassBean(classId).findById();
        ClassBean helperClass = new ClassBean();
        String classCode = cb.getClassCodeById();
        if (helperClass.deleteById(cb)) {
            response.sendRedirect("admin/view_edit_delete/ClassViewDelete.jsp?edit&delete&message=Successfully removed the Class: " + classCode + "!&result=success");
        } else {
            response.sendRedirect("admin/view_edit_delete/ClassViewDelete.jsp?edit&delete&message=Failed to remove the Class: " + classCode + "!&result=fail");
        }
    }

    private void addSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String subjectCode, subjectName;
        subjectCode = req.getParameter("subject_code");
        subjectName = req.getParameter("subject_name");
        SubjectBean sb = new SubjectBean();
        sb.setSubjectCode(subjectCode);
        sb.setSubjectName(subjectName);
        if (sb.persist(sb)) {
            resp.sendRedirect("admin/add/SubjectAddEdit.jsp?message=Successfully added the subject!&result=success");
        } else {
            resp.sendRedirect("admin/add/SubjectAddEdit.jsp?message=Failed to add the subject!&result=fail");
        }
    }

    private void editSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String subjectCode, subjectName;
        long subjectId;
        subjectId = Long.parseLong(req.getParameter("subject_id"));
        subjectCode = req.getParameter("subject_code");
        subjectName = req.getParameter("subject_name");
        SubjectBean sb = new SubjectBean(subjectId);
        sb.setSubjectCode(subjectCode);
        sb.setSubjectName(subjectName);
        if (sb.merge(sb)) {
            resp.sendRedirect("admin/add/SubjectAddEdit.jsp?message=Successfully added the subject!&result=success");
        } else {
            resp.sendRedirect("admin/add/SubjectAddEdit.jsp?message=Failed to add the subject!&result=fail");
        }
    }

//    private void editSchema(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        long classId, subjectId;
//        classId = Long.parseLong(req.getParameter("class_id"));
//        subjectId = Long.parseLong(req.getParameter("subject_id"));
//        SubjectBean sb = new SubjectBean(subjectId);
//        ClassBean cb = new ClassBean(classId).findById();
//        cb.getSubjectBeanCollection().add(sb);
//        if (cb.merge(cb)) {
//            resp.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + classId + "&message=Successfully added the schema!&result=success");
//        } else {
//            resp.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + classId + "&message=Failed to add the schema!&result=fail");
//        }
//    }
    private void editSchema(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long classId;
        classId = Long.parseLong(req.getParameter("class_id"));
        ClassBean cb = new ClassBean(classId).findById();
        String[] subjectIdArray = req.getParameterValues("subject_id");
        if (subjectIdArray == null) {
            resp.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + classId + "&message=Failed to add the schema!&result=fail");
            return;
        }
        for (int i = 0; i < subjectIdArray.length; i++) {
            long subjectId = Long.parseLong(subjectIdArray[i]);
            SubjectBean sb = new SubjectBean(subjectId);
            cb.getSubjectBeanCollection().add(sb);
        }
        if (cb.merge(cb)) {
            resp.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + classId + "&message=Successfully added the schema!&result=success");
        } else {
            resp.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + classId + "&message=Failed to add the schema!&result=fail");
        }
    }

    private void deleteSchemaByClassIdAndSubjectId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClassBean cb = new ClassBean(Long.parseLong(request.getParameter("class_id"))).findById();
        SubjectBean sb = new SubjectBean(Long.parseLong(request.getParameter("subject_id")));
        boolean success = cb.getSubjectBeanCollection().remove(sb);
        if (success) {
            if (cb.merge(cb)) {
                response.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + cb.getId() + "&result=success&message=Schema Edited successfully. Removed Subject: " + sb.findById().getSubjectCode());
            } else {
                response.sendRedirect("admin/add/SchemaEdit.jsp?edit=" + cb.getId() + "&result=fail&message=Could not remove subject for class! Subject: " + sb.findById().getSubjectCode());
            }
        }
    }

    private void editCSF(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long classId;
        classId = Long.parseLong(req.getParameter("class_id"));
        ClassBean cb = new ClassBean(classId).findById();
        cb.getCsfBeanCollection().clear();
        Collection<SubjectBean> sbCollection = cb.getSubjectBeanCollection();
        for (SubjectBean sb : sbCollection) {
            String[] facultyIdArray = req.getParameterValues("faculty_id" + sb.getId());
            if (facultyIdArray == null) {
                continue;
            }
            for (int i = 0; i < facultyIdArray.length; i++) {
                long facultyId = Long.parseLong(facultyIdArray[i]);
                FacultyBean fb = new FacultyBean(facultyId);
                CsfBean csfBean = new CsfBean();
                csfBean.setClassId(cb);
                csfBean.setSubjectId(sb);
                csfBean.setSessionId(UtilsManager.getCurrentSessionId());
                csfBean.setFacultyId(fb);
                if (csfBean.findByClassIdAndSubjectIdAndSessionIdAndFacultyId().size() == 0) {
                    cb.getCsfBeanCollection().add(csfBean);
                }
            }
        }
        if (cb.merge(cb)) {
            resp.sendRedirect("admin/add/CSFEdit.jsp?edit=" + classId + "&message=Successfully added the CSF!&result=success");
        } else {
            resp.sendRedirect("admin/add/CSFEdit.jsp?edit=" + classId + "&message=Failed to add the CSF!&result=fail");
        }
    }

    private void deleteCSFById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long csfId = Long.parseLong(request.getParameter("csf_id"));
        CsfBean csf = new CsfBean(csfId).findById();
        long classId = csf.getClassId().getId();
        CsfBean helperCsf = new CsfBean();
        if (helperCsf.deleteById(csf)) {
            response.sendRedirect("admin/add/CSFEdit.jsp?edit=" + classId + "&message=Successfully removed the CSF!&result=success");
        } else {
            response.sendRedirect("admin/add/CSFEdit.jsp?edit=" + classId + "&message=Failed to remove the CSF!&result=fail");
        }
    }

    private void changeAdminUsernamePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionManager sm = new SessionManager(request, response, false);
        FacultyBean faculty = sm.getFaculty().findById();
//        LoginBean lb  = new LoginBean().findByFacultyId(faculty);
        faculty.getLoginBeanCollection().iterator().next().setUsername(request.getParameter("username"));
        faculty.getLoginBeanCollection().iterator().next().setPassword(request.getParameter("password"));
        ;
//        LoginBean lb = new LoginBean(sm.getFaculty().getLoginBeanCollection().iterator().next().getUsername(),
//                sm.getFaculty().getLoginBeanCollection().iterator().next().getPassword()).findByUsernameAndPassword();
//        lb.setUsername(request.getParameter("username"));
//        lb.setPassword(request.getParameter("password"));
        if (faculty.merge(faculty)) {
            response.sendRedirect("admin/temp/ChangeAdminPassword.jsp?message=Successfully changed the username password!&result=success");
        } else {
            response.sendRedirect("admin/temp/ChangeAdminPassword.jsp?message=Failed to change the username password!&result=fail");
        }
    }

    private void deleteFaculty(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long facultyId = Long.parseLong(request.getParameter("faculty_id"));
        FacultyBean fb = new FacultyBean(facultyId).findById();
        FacultyBean helperFaculty = new FacultyBean();
        String facultyName = fb.getFirstName() + " " + fb.getLastName();
        if (helperFaculty.deleteById(fb)) {
            response.sendRedirect("admin/view_edit_delete/FacultyViewDelete.jsp?edit&delete&message=Successfully removed the Faculty: " + facultyName + "!&result=success");
        } else {
            response.sendRedirect("admin/view_edit_delete/FacultyViewDelete.jsp?edit&delete&message=Failed to remove the Faculty: " + facultyName + "!&result=fail");
        }
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long departmentId = Long.parseLong(request.getParameter("department_id"));
        DepartmentBean db = new DepartmentBean(departmentId).findById();
        DepartmentBean helperDepartment = new DepartmentBean();
        String departmentName = db.getDepartmentCode()+ ": " + db.getDepartmentName();
        if (helperDepartment.deleteById(db)) {
            response.sendRedirect("admin/view_edit_delete/DepartmentViewDelete.jsp?edit&delete&message=Successfully removed the Department: " + departmentName + "!&result=success");
        } else {
            response.sendRedirect("admin/view_edit_delete/DepartmentViewDelete.jsp?edit&delete&message=Failed to remove the Department: " + departmentName + "!&result=fail");
        }
    }

}

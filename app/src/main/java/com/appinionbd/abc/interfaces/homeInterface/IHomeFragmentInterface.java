package com.appinionbd.abc.interfaces.homeInterface;

import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

public interface IHomeFragmentInterface {
    void successful(List<TaskCategory> taskCategories);
    void noNewInfo(String message);
    void unAuthorized(String message);
    void error(String message);
    void connectionProblem(String message);
}

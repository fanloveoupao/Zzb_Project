package com.example;

import com.example.exceptions.ErrorCode;
import com.google.gson.annotations.Expose;

/**
 * Created by fan-gk on 2017/2/3.
 */

public class BaseBean<T> extends EmptyBean {
    @Expose
    public T data;

    public BaseBean(){ super(); }
    public BaseBean(ErrorCode code){ super(code); }
    public BaseBean(T data){
        super(ErrorCode.NONE);
        this.data = data;
    }
}

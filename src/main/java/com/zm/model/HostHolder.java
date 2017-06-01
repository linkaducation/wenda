package com.zm.model;

import org.springframework.stereotype.Component;

/**
 * Created by Ellen on 2017/5/21.
 */
@Component
public class HostHolder {
    private ThreadLocal<User> map = new ThreadLocal<User>();

    public User getUser() {
        return map.get();
    }

    public void setUser(User user) {
        map.set(user);
    }

    public void clear() {
        map.remove();
    }
}

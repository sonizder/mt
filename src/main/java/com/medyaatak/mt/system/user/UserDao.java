package com.medyaatak.mt.system.user;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ginnun
 * Date: 8/18/11
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */

public interface UserDao {
    public List<User> getList();

    public void delete(User user);

    public User update(User updatedUser);

    public User save(User newUser);

    public User findByName(String name);

    public List<User> findByLikeName(String query);

    public User findById(long selectedId);
}

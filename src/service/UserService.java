package service;

import model.User;

import org.springframework.transaction.annotation.Transactional;

import dao.BaseDao;

@Transactional
public interface UserService {

	public void setUserDao(BaseDao<User> userDao);

	/**
	 * 保存用户数据，用于注册
	 * 
	 * @param user
	 * @return
	 */
	public boolean save(User user);

	public boolean update(User loginedUser, User user);

	public User query(User t);

	/**
	 * 检验当前User是否已经存在 通过用户名和学号进行查询
	 * 
	 * @param user
	 * @return
	 */
	public int checkUsereExists(User user);

	public User checkLogin(User user);


}
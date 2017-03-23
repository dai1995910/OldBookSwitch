package service.impl;

import model.User;

import org.springframework.transaction.annotation.Transactional;

import service.UserService;
import dao.BaseDao;

@Transactional
public class UserServiceImpl implements UserService {

	private BaseDao<User> userDao;

	public void setUserDao(BaseDao<User> userDao) {
		this.userDao = userDao;
	}

	/**
	 * 保存用户数据，用于注册
	 * 
	 * @param user
	 * @return
	 */
	public boolean save(User user) {
		// 检查数据是否完整
		boolean result = false;
		result = checkBeanValue(user.getContact())
				& checkBeanValue(user.getPassword())
				& checkBeanValue(user.getStuId())
				& checkBeanValue(user.getUsername());

		return result && userDao.save(user);
	}

	/**
	 * 用于保存用户修改的数据
	 */
	public boolean update(User loginedUser, User user) {
		// 获取到用户修改的数据
		String newContact = user.getContact();
		String userPwd = user.getPassword();
		if(user == null || newContact == null || newContact.equals("") || userPwd == null || userPwd.equals("")) {
			return false;
		}
		loginedUser.update(user);
		return userDao.update(loginedUser);
	}

	public User query(User t) {
		return userDao.query(t);
	}

	/**
	 * 检验当前User是否已经存在 通过用户名和学号进行查询
	 * 
	 * @param user
	 * @return
	 */
	public int checkUsereExists(User user) {
		int result = 0;
		User testUser = new User();
		// 通过用户名进行查询
		if (!"".equals(user.getUsername()) && user.getUsername() != null) {
			testUser.setUsername(user.getUsername());
			User result1 = userDao.query(testUser);
			// 进行结果判断
			if (result1 != null) {
				// 说明用户名已经被占用了
				result = 1;
			}
		}
		// 通过学号进行查询
		if (!"".equals(user.getStuId()) && user.getStuId() != null) {
			testUser.setUsername(null);
			testUser.setStuId(user.getStuId());
			User result2 = userDao.query(testUser);
			if (result2 != null) {
				result = result == 1 ? 3 : 2;
			}
		}
		return result;
	}

	/**
	 * 检查登录 使用用户名进行登录
	 */
	@Override
	public User checkLogin(User user) {
		User tempUser = new User();
		// 判断账号和密码是否为空
		if (user.getUsername() != null && !"".equals(user.getUsername())
				&& user.getPassword() != null && !"".equals(user.getPassword())) {
			// 通过username获取到数据库中的数据
			tempUser.setUsername(user.getUsername());
			User savedUser = userDao.query(tempUser);
			// 判断是否存在用户
			if (savedUser == null) {
				// 说明不存在这个用户
				return null;
			} else {
				if (savedUser.getPassword().equals(user.getPassword())) {
					// 说明密码正确
					return savedUser;
				} else {
					// 说明密码错误
					return null;
				}
			}
		}
		return null;
	}

	/*
	 * 检测字符串是否为空
	 */
	private boolean checkBeanValue(String data) {
		if (data == null || "".equals(data)) {
			System.out.println("注册时有数据为空为空");
			return false;
		}
		return true;
	}

}

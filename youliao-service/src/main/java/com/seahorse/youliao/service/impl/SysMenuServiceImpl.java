package com.seahorse.youliao.service.impl;

import com.seahorse.youliao.dao.SysMenuDao;
import com.seahorse.youliao.dao.entity.SysMenuDO;
import com.seahorse.youliao.enums.MenulTypeEnum;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.service.SysMenuService;
import com.seahorse.youliao.service.entity.SysMenuDTO;
import com.seahorse.youliao.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* describe: 菜单权限表
* @author : songqiang
* @date: 2020-01-17 09:23:45.259
**/
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDTO> implements SysMenuService {

	private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);
	
	@Autowired
	private SysMenuDao sysMenuDao;

	/**
	 * 新增菜单
	 *
	 * @param menuDTO
	 * @return
	 */
	@Override
	public boolean insertMenu(SysMenuDTO menuDTO) {

		//校验菜单数据
		checkMenuData(menuDTO);
		return sysMenuDao.insert(BeanUtil.convert(menuDTO,SysMenuDO.class)) > 0;
	}


	/**
	 * 校验菜单新增编辑时 参数
	 * 由于前端是动态form表单
	 * validation注解+后台控制参数校验
	 * @param menuDTO
	 */
	private void checkMenuData(SysMenuDTO menuDTO) {

		//一级菜单 pid 默认为0
		if(MenulTypeEnum.ROOT_MENU.getValue().equals(menuDTO.getType())){
			//一级菜单pid=0
			menuDTO.setPid(0);
			if(StringUtils.isBlank(menuDTO.getRedirect())){
				throw new BusinessException("跳转路径不能为空");
			}
			if(StringUtils.isBlank(menuDTO.getIcon())){
				throw new BusinessException("菜单图标不能为空");
			}
		}else{
			if(menuDTO.getPid() == null){
				throw new BusinessException("上级菜单不能为空");
			}
		}

		//类型为菜单时 请求地址不能为空
		if(!MenulTypeEnum.BUTTON.getValue().equals(menuDTO.getType())){
			if(StringUtils.isBlank(menuDTO.getPath())){
				throw new BusinessException("菜单路径不能为空");
			}
			if(StringUtils.isBlank(menuDTO.getComponent())){
				throw new BusinessException("前端路由不能为空");
			}
			if(StringUtils.isBlank(menuDTO.getComponentName())){
				throw new BusinessException("路由名称不能为空");
			}
		}
		//按钮时 权限标识不能为空
		if(MenulTypeEnum.BUTTON.getValue().equals(menuDTO.getType())){
			if(StringUtils.isBlank(menuDTO.getPermission())){
				throw new BusinessException("权限标识不能为空");
			}
			//判断是否存在相同按钮标识
			int num = sysMenuDao.checkPermission(menuDTO.getPermission(),menuDTO.getId());
			if(num > 1){
				logger.error("权限标识重复 permission = " + menuDTO.getPermission());
				throw new BusinessException("权限标识重复");
			}
		}
	}

	/**
	 * 编辑菜单
	 *
	 * @param menuDTO
	 * @return
	 */
	@Override
	public boolean updateMenu(SysMenuDTO menuDTO) {

		//校验菜单数据
		checkMenuData(menuDTO);
		return sysMenuDao.update(BeanUtil.convert(menuDTO,SysMenuDO.class)) > 0;
	}

	/**
	 * 批量删除菜单
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public boolean deleteBatch(List<Integer> ids) {

		List<Integer> idList = new ArrayList<>();
		for (Integer id : ids) {
			//查看是否为子菜单
			List<SysMenuDO> list = sysMenuDao.getListByPid(id);
			if(CollectionUtils.isEmpty(list)){
				//不存在子节点了 则删除
				idList.add(id);
			}
		}
		return sysMenuDao.deleteByIds(idList) > 0;
	}

	/**
	 * 删除单条
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteById(Integer id) {

		List<Integer> idList = new ArrayList<>();
		//查看是否为子菜单
		List<SysMenuDO> list = sysMenuDao.getListByPid(id);
		if(!CollectionUtils.isEmpty(list)){
			throw new BusinessException("存在子菜单 无法删除");
		}
		//不存在子节点了 则删除
		idList.add(id);
		return sysMenuDao.deleteByIds(idList) > 0;

	}

	/**
	 * 查询用户菜单
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysMenuDTO> findMenuByUserId(Integer userId) {

		List<SysMenuDO> list = sysMenuDao.findMenuByUserId(userId);
		//由于用户对应多个角色 菜单去重
		List<SysMenuDO> distinct = list.stream().distinct().collect(Collectors.toList());
		return BeanUtil.convert(distinct,SysMenuDTO.class);
	}

	/**
	 * 查询用户权限
	 * @param userId
	 * @return
	 */
	@Override
	public List<String> getPermissionsByUserId(Integer userId) {

		return sysMenuDao.getPermissionsByUserId(userId);
	}
}

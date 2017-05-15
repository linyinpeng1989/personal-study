package com.headfirst.memento.multistate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Client {
	public static void main(String[] args) {
		Originator ori = new Originator();
		Caretaker caretaker = new Caretaker();
		ori.setState1("中国");
		ori.setState2("强盛");
		ori.setState3("繁荣");
		System.out.println("===初始化状态===\n" + ori);

		caretaker.setMemento("001", ori.createMemento());
		
		ori.setState1("软件");
		ori.setState2("架构");
		ori.setState3("优秀");
		System.out.println("===修改后状态===\n" + ori);

		ori.restoreMemento(caretaker.getMemento("001"));
		System.out.println("===恢复后状态===\n" + ori);

	}
}

/**
 * @描述：备忘录，负责存储发起人对象的内部状态，在需要的时候提供发起人需要的内部状态。
 * @创建时间：2015-10-26 下午4:30:01
 */
class Memento {
	private Map<String, Object> stateMap;

	public Memento(Map<String, Object> stateMap) {
		this.stateMap = stateMap;
	}

	public Map<String, Object> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<String, Object> stateMap) {
		this.stateMap = stateMap;
	}
}

/**
 * @描述：发起人，记录当前时刻的内部状态，负责定义哪些属于备份范围的状态，负责创建和恢复备忘录数据。
 * @创建时间：2015-10-26 下午4:25:53
 */
class Originator {
	private String state1 = "";
	private String state2 = "";
	private String state3 = "";

	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public String getState3() {
		return state3;
	}

	public void setState3(String state3) {
		this.state3 = state3;
	}

	public Memento createMemento() {
		return new Memento(BeanUtils.backupProp(this));
	}

	public void restoreMemento(Memento memento) {
		BeanUtils.restoreProp(this, memento.getStateMap());
	}

	public String toString() {
		return "state1=" + state1 + "state2=" + state2 + "state3=" + state3;
	}

}

/**
 * @描述：管理角色，对备忘录进行管理，保存和提供备忘录。
 * @创建时间：2015-10-26 下午4:32:18
 */
class Caretaker {
	private Map<String, Memento> memMap = new HashMap<String, Memento>();

	public Memento getMemento(String index) {
		return memMap.get(index);
	}

	public void setMemento(String index, Memento memento) {
		this.memMap.put(index, memento);
	}
}

class BeanUtils {
	public static Map<String, Object> backupProp(Object bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor des : descriptors) {
				String fieldName = des.getName();
				Method getter = des.getReadMethod();
				Object fieldValue = getter.invoke(bean, new Object[] {});
				if (!fieldName.equalsIgnoreCase("class")) {
					result.put(fieldName, fieldValue);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void restoreProp(Object bean, Map<String, Object> propMap) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor des : descriptors) {
				String fieldName = des.getName();
				if (propMap.containsKey(fieldName)) {
					Method setter = des.getWriteMethod();
					setter.invoke(bean, new Object[] { propMap.get(fieldName) });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
package com.linyp.study.websocket;

import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by linyp on 2016/8/28.
 * WebSocket消息处理类
 */
public class SystemWebSocketHandler implements WebSocketHandler {
    private static Map<Long, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributesMap = session.getAttributes();
        if(null == attributesMap || null == attributesMap.get("doctorUserId")){
            //logger.error("没有用户信息，连接关闭。");
            session.close();
            return;
        }
        Long doctorUserId = (Long) attributesMap.get("doctorUserId");
        TextMessage textMessage = null;
        if(doctorUserId == null){
            textMessage = new TextMessage("连接失败，用户未登陆");
            //logger.info("用户未登陆，关闭链接");
            session.sendMessage(textMessage);
            session.close();
            return;
        }
        webSocketSessionMap.put(doctorUserId, session);
        //logger.info("用户登录连接socket成功，doctorUserId = {}", doctorUserId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //用户主动获取消息的时候就获取当前用户未读的消息数
        Map<String, Object> attributesMap = session.getAttributes();
        if(null == attributesMap || null == attributesMap.get("doctorUserId")){
            session.close();
            return;
        }
        Long doctorUserId = (Long) attributesMap.get("doctorUserId");
        //获取咨询未读消息
        String messageValue = String.valueOf(message.getPayload());
        for (Long docUserId : webSocketSessionMap.keySet()) {
            if (doctorUserId.longValue() == docUserId.longValue())
                continue;
            WebSocketSession other = webSocketSessionMap.get(docUserId);
            other.sendMessage(new TextMessage(messageValue));
        }
        /*SocketMessageReqEntity socketMessageReqEntity = JsonUtils.fromJSON(messageValue, SocketMessageReqEntity.class);
        SocketRequestTypeEnum bizCode = SocketRequestTypeEnum.getTypeEnum(socketMessageReqEntity.getBizCode());

        String groupKey = socketMessageReqEntity.getGroupKey();
        ResponseDO<GroupConsultRoomInfo> groupConsultRoomInfo = null;
        GroupConsultRoomInfo roomInfo = null;
        switch (bizCode){
            case READ_GROUP_CONSULT_MSG :
                logger.info("请求想获取未读的会诊消息，groupKey = {}", socketMessageReqEntity.getValue());
                Set<String> messages = this.doctorMessageChacheManager.queryMsgs(doctorUserId, SocketRequestTypeEnum.READ_GROUP_CONSULT_MSG.getCode());
                for(String messageOne : messages){
                    TextMessage textMessage = new TextMessage(messageOne);
                    session.sendMessage(textMessage);
                }
                break;
            case GET_ECHAT_ROOM_INFO:
                logger.info("请求想获取会诊室的信息，groupKey = {}", socketMessageReqEntity.getValue());
                groupConsultRoomInfo = groupConsultManager.getGroupConsultRoomInfo(groupKey, doctorUserId);
                roomInfo = groupConsultRoomInfo.getDataResult();
                if(!groupConsultRoomInfo.isSuccess() || null == roomInfo){
                    logger.info("获取会诊室信息失败，groupKey = {}", groupKey);
                    return;
                }
                DoctorMessageEntity messageEntity = new DoctorMessageEntity();
                messageEntity.setMessageId(UUID.randomUUID().toString());
                messageEntity.setDoctorUserId(doctorUserId);
                messageEntity.setSendUserId(doctorUserId);
                messageEntity.setType(SocketMessageTypeEnum.GROUPC_ONSULT_ECHAT_INFO.getType());

                SocketMessageEchatInfo socketMessageEchatInfo = new SocketMessageEchatInfo();
                socketMessageEchatInfo.setBizId(roomInfo.getEchatId());
                socketMessageEchatInfo.setRid(roomInfo.getDoctorsIds());
                messageEntity.setMessageEntity(socketMessageEchatInfo);
                //回复给发送者
                logger.info("socket消息回复：messageEntity : {}", JsonUtils.toJSON(messageEntity));
                TextMessage repTextMessage = new TextMessage(JsonUtils.toJSON(messageEntity));
                session.sendMessage(repTextMessage);
                break;
            case GET_ECHAT_INVIT_MSG:
                logger.info("发送视频邀请信息");
                String roomId = socketMessageReqEntity.getValue();
                if(StringUtils.isBlank(groupKey)){
                    logger.error("请求信息不正常。groupKey = {}, roomId = {}, doctorUserId = {}", groupKey, roomId, doctorUserId);
                    return;
                }
                groupConsultRoomInfo = groupConsultManager.getGroupConsultRoomInfo(groupKey, doctorUserId);
                roomInfo = groupConsultRoomInfo.getDataResult();
                if(!groupConsultRoomInfo.isSuccess() || null == roomInfo){
                    logger.info("获取会诊室信息失败，groupKey = {}", groupKey);
                    return;
                }
                List<Long> doctorLists = roomInfo.getDoctorIdList();
                if(null == doctorLists || doctorLists.isEmpty()){
                    return;
                }
                String inviteDoctorName = String.valueOf(attributesMap.get(BizConstant.SOCKET_USER_NAME_ATTR));
                for(Long inviteeDoctorUserId : doctorLists){
                    doctorMessageManager.inviteDoctorEchat(doctorUserId, inviteDoctorName, inviteeDoctorUserId, groupKey, roomId);
                }
                break;
            case CANCLE_ECHAT_MSG:
                logger.info("取消视频");
                break;
            case OUTSIDE_ROOM :
                logger.info("退出会诊室。");
                doctorClinicRoomCacheManager.outRoom(doctorUserId, groupKey);
                break;
            default:
                logger.info("无效的socket请求，message = {}", messageValue);
                break;
        }*/
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Map<String, Object> attributesMap = session.getAttributes();
        if(null == attributesMap || null == attributesMap.get("doctorUserId")){
            //logger.error("没有用户信息，连接关闭。");
            session.close();
            return;
        }
        Long doctorUserId = (Long) attributesMap.get("doctorUserId");
        webSocketSessionMap.remove(doctorUserId);
        //记录错误日志等操作
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Map<String, Object> attributesMap = session.getAttributes();
        if(null == attributesMap || null == attributesMap.get("doctorUserId")){
            //logger.error("没有用户信息，连接关闭。");
            session.close();
            return;
        }
        Long doctorUserId = (Long) attributesMap.get("doctorUserId");
        webSocketSessionMap.remove(doctorUserId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

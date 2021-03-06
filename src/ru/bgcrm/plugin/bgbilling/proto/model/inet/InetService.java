package ru.bgcrm.plugin.bgbilling.proto.model.inet;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ru.bgcrm.model.IdTitleTreeItem;
import ru.bgcrm.util.Utils;
import ru.bgcrm.util.inet.IPUtils;

public class InetService extends IdTitleTreeItem<InetService> {
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_CLOSED = 1;
    public static final int STATUS_LOCKED = 2;

    /**
     * Состояние сервиса - означает, что состояние не изменилось.
     */
    public static final short STATE_NULL = -1000;

    /**
     * Состояние сервиса - удален (со связанных устройств).
     */
    public static final short STATE_DELETED = -1;

    /**
     * Состояние сервиса/соединения - доступ отключен.
     */
    public static final short STATE_DISABLE = 0;

    /**
     * Состояние сервиса/соединения - доступ включен.
     */
    public static final short STATE_ENABLE = 1;

    private int contractId;
    private int parentId;
    private int typeId;
    private String typeTitle;
    private String login;
    private int deviceId;
    private String deviceTitle;
    private int ipResId;
    private byte[] addrFrom;
    private byte[] addrTo;
    private List<byte[]> macAddressList;
    private int ifaceId;
    private String interfaceTitle;
    private int vlan = -1;
    private int status;
    private int sessionCountLimit;
    private Date dateFrom;
    private Date dateTo;
    private short devState;
    private int accessCode;
    private String accessCodeTitle;
    private String comment;

    public int getContractId() {
        return contractId;
    }
    
    public int getCid() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    /** Синоним для совместимости с сервисом биллинга. */
    public String getUname() {
        return login;
    }

    /** Синоним для совместимости с сервисом биллинга. */
    public void setUname(String login) {
        this.login = login;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
    
    /** Синоним для совместимости с сервисом биллинга. */
    public int getDid() {
        return deviceId;
    }

    /** Синоним для совместимости с сервисом биллинга. */
    public void setDid(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceTitle() {
        return deviceTitle;
    }

    public void setDeviceTitle(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }
    
    public int getIpResId() {
        return ipResId;
    }

    public void setIpResId(int ipResourceId) {
        this.ipResId = ipResourceId;
    }

    public byte[] getAddrFrom() {
        return addrFrom;
    }

    public void setAddrFrom(byte[] addrFrom) {
        this.addrFrom = addrFrom;
    }

    public byte[] getAddrTo() {
        return addrTo;
    }

    public void setAddrTo(byte[] addrTo) {
        this.addrTo = addrTo;
    }

    public String getAddrFromStr() {
        return IPUtils.convertIpToString(Utils.convertBytesToInt(addrFrom));
    }

    public void setAddrFromStr(String addrFrom) {
        this.addrFrom = Utils.convertIntToBytes(IPUtils.convertStringIPtoInt(addrFrom));
    }

    public String getAddrToStr() {
        return IPUtils.convertIpToString(Utils.convertBytesToInt(addrTo));
    }

    public void setAddrToStr(String addrTo) {
        this.addrTo = Utils.convertIntToBytes(IPUtils.convertStringIPtoInt(addrTo));;
    }

    public int getIfaceId() {
        return ifaceId;
    }

    public void setIfaceId(int ifaceId) {
        this.ifaceId = ifaceId;
    }
    
    public String getInterfaceTitle() {
        return interfaceTitle;
    }

    public void setInterfaceTitle(String interfaceTitle) {
        this.interfaceTitle = interfaceTitle;
    }

    /**
     * Возвращает первый MAC адрес.
     * @return
     */
    public byte[] getMacAddress() {
        return Utils.getFirst(macAddressList);
    }

    /**
     * Устанавливает единственный MAC адрес.
     * @param macAddress
     */
    public void setMacAddress(byte[] macAddress) {
        setMacAddressList(Collections.singletonList(macAddress));
    }
    
    /**
     * Возвращает первый MAC адрес.
     * @return
     */
    public String getMacAddressStr() {
        return InetUtils.macAddressToString(Utils.getFirst(macAddressList));
    }
    
    /**
     * Устанавливает единственный MAC адрес.
     * @param macAddress
     */
    public void setMacAddressStr(String macAddress) {
        setMacAddressList(Collections.singletonList(InetUtils.parseMacAddress(macAddress)));
    }

    public List<byte[]> getMacAddressList() {
        return this.macAddressList;
    }

    public void setMacAddressList(List<byte[]> macAddress) {
        this.macAddressList = macAddress;
    }

    public int getVlan() {
        return vlan;
    }

    public void setVlan(int vlan) {
        this.vlan = vlan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusTitle() {
        if (parentId > 0) {
            return "";
        }

        switch (status) {
            case STATUS_ACTIVE: {
                return "открыт";
            }
            case STATUS_CLOSED: {
                return "закрыт";
            }
            case STATUS_LOCKED: {
                return "заблокирован";
            }
            default: {
                return "---";
            }
        }
    }

    public int getSessionCountLimit() {
        return sessionCountLimit;
    }

    public void setSessionCountLimit(int sessionCountLimit) {
        this.sessionCountLimit = sessionCountLimit;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public short getDevState() {
        return devState;
    }

    public void setDevState(short devState) {
        this.devState = devState;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public String getAccessCodeTitle() {
        return accessCodeTitle;
    }

    public void setAccessCodeTitle(String accessCodeTitle) {
        this.accessCodeTitle = accessCodeTitle;
    }

    public String getDevStateTitle() {
        if (parentId > 0) {
            return "";
        }

        switch (devState) {
            case STATE_DELETED: {
                return "удален";
            }
            case STATE_ENABLE: {
                return "включен";
            }
            case STATE_DISABLE: {
                if (accessCodeTitle != null) {
                    return "отключен [" + accessCodeTitle + "]";
                } else {
                    return "отключен";
                }
            }
            default: {
                return "---";
            }
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
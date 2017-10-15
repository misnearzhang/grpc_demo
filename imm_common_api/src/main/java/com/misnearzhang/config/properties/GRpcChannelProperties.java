package com.misnearzhang.config.properties;

public class GRpcChannelProperties {
    public static final String GRPC_DEFAULT_HOST = "";

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public void setPlaintext(boolean plaintext) {
        this.plaintext = plaintext;
    }

    public void setEnableKeepAlive(boolean enableKeepAlive) {
        this.enableKeepAlive = enableKeepAlive;
    }

    public void setKeepAliveDelay(long keepAliveDelay) {
        this.keepAliveDelay = keepAliveDelay;
    }

    public void setKeepAliveTimeout(long keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GRpcChannelProperties)) {
            return false;
        }
        GRpcChannelProperties other = (GRpcChannelProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$serverHost = getServerHost();
        Object other$serverHost = other.getServerHost();
        if (this$serverHost == null ? other$serverHost != null : !this$serverHost.equals(other$serverHost)) {
            return false;
        }
        Object this$serverPort = getServerPort();
        Object other$serverPort = other.getServerPort();
        if (this$serverPort == null ? other$serverPort != null : !this$serverPort.equals(other$serverPort)) {
            return false;
        }
        if (isPlaintext() != other.isPlaintext()) {
            return false;
        }
        if (isEnableKeepAlive() != other.isEnableKeepAlive()) {
            return false;
        }
        if (getKeepAliveDelay() != other.getKeepAliveDelay()) {
            return false;
        }
        return getKeepAliveTimeout() == other.getKeepAliveTimeout();
    }

    protected boolean canEqual(Object other) {
        return other instanceof GRpcChannelProperties;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $serverHost = getServerHost();
        result = result * 59 + ($serverHost == null ? 43 : $serverHost.hashCode());
        Object $serverPort = getServerPort();
        result = result * 59 + ($serverPort == null ? 43 : $serverPort.hashCode());
        result = result * 59 + (isPlaintext() ? 79 : 97);
        result = result * 59 + (isEnableKeepAlive() ? 79 : 97);
        long $keepAliveDelay = getKeepAliveDelay();
        result = result * 59 + (int) ($keepAliveDelay >>> 32 ^ $keepAliveDelay);
        long $keepAliveTimeout = getKeepAliveTimeout();
        result = result * 59 + (int) ($keepAliveTimeout >>> 32 ^ $keepAliveTimeout);
        return result;
    }

    public String toString() {
        return "GRpcChannelProperties(serverHost=" + getServerHost() + ", serverPort=" + getServerPort() + ", plaintext=" + isPlaintext() + ", enableKeepAlive=" + isEnableKeepAlive() + ", keepAliveDelay=" + getKeepAliveDelay() + ", keepAliveTimeout=" + getKeepAliveTimeout() + ")";
    }

    public static final Integer GRPC_DEFAULT_PORT = Integer.valueOf(7575);
    public static final GRpcChannelProperties DEFAULT = new GRpcChannelProperties();

    public String getServerHost() {
        return this.serverHost;
    }

    private String serverHost = "";

    public Integer getServerPort() {
        return this.serverPort;
    }

    private Integer serverPort = GRPC_DEFAULT_PORT;

    public boolean isPlaintext() {
        return this.plaintext;
    }

    private boolean plaintext = true;

    public boolean isEnableKeepAlive() {
        return this.enableKeepAlive;
    }

    private boolean enableKeepAlive = true;

    public long getKeepAliveDelay() {
        return this.keepAliveDelay;
    }

    private long keepAliveDelay = 10L;

    public long getKeepAliveTimeout() {
        return this.keepAliveTimeout;
    }

    private long keepAliveTimeout = 120L;
}

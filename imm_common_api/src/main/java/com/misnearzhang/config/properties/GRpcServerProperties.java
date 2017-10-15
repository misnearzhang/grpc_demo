package com.misnearzhang.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("grpc.server")
public class GRpcServerProperties {

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsePlainText(Boolean usePlainText) {
        this.usePlainText = usePlainText;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GRpcServerProperties)) {
            return false;
        }
        GRpcServerProperties other = (GRpcServerProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (getPort() != other.getPort()) {
            return false;
        }
        Object this$address = getAddress();
        Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
            return false;
        }
        Object this$usePlainText = getUsePlainText();
        Object other$usePlainText = other.getUsePlainText();
        return this$usePlainText == null ? other$usePlainText == null : this$usePlainText.equals(other$usePlainText);
    }

    protected boolean canEqual(Object other) {
        return other instanceof GRpcServerProperties;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + getPort();
        Object $address = getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        Object $usePlainText = getUsePlainText();
        result = result * 59 + ($usePlainText == null ? 43 : $usePlainText.hashCode());
        return result;
    }

    public String toString() {
        return "GRpcServerProperties(port=" + getPort() + ", address=" + getAddress() + ", usePlainText=" + getUsePlainText() + ")";
    }

    public int getPort() {
        return this.port;
    }

    private int port = 7575;

    public String getAddress() {
        return this.address;
    }

    private String address = "0.0.0.0";

    public Boolean getUsePlainText() {
        return this.usePlainText;
    }

    private Boolean usePlainText = Boolean.valueOf(true);
}

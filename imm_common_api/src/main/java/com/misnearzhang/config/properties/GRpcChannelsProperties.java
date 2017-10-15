package com.misnearzhang.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("grpc")
public class GRpcChannelsProperties {

    public String toString() {
        return "GRpcChannelsProperties(client=" + getClient() + ")";
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $client = getClient();
        result = result * 59 + ($client == null ? 43 : $client.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {

        return other instanceof GRpcChannelsProperties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GRpcChannelsProperties)) {
            return false;
        }
        GRpcChannelsProperties other = (GRpcChannelsProperties) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$client = getClient();
        Object other$client = other.getClient();
        return this$client == null ? other$client == null : this$client.equals(other$client);
    }

    public void setClient(Map<String, GRpcChannelProperties> client) {
        this.client = client;
    }

    public Map<String, GRpcChannelProperties> getClient() {
        return this.client;
    }

    private Map<String, GRpcChannelProperties> client = new HashMap();

    public GRpcChannelProperties getChannel(String name) {
        return (GRpcChannelProperties) this.client.getOrDefault(name, GRpcChannelProperties.DEFAULT);
    }
}

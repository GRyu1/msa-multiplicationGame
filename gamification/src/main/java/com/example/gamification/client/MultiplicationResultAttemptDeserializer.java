package com.example.gamification.client;

import com.example.gamification.client.dto.MultiplicationResultAttempt;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {
    @Override
    public MultiplicationResultAttempt deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode jsonNode = objectCodec.readTree(p);
        return new MultiplicationResultAttempt(jsonNode.get("player").get("alias").asText(),
                jsonNode.get("multiplication").get("factorA").asInt(),
                jsonNode.get("multiplication").get("factorB").asInt(),
                jsonNode.get("resultAttempt").asInt(),
                jsonNode.get("correct").asBoolean());
    }
}

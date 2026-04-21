package com.yupi.liangdada.manager;


import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.*;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class AiManager {


    @Resource
    private ZhipuAiClient zhipuAiClient;

    // 稳定的随机数
    private static final float STABLE_TEMPERATURE = 0.15f;

    // 不稳定的随机数
    private static final float UNSTABLE_TEMPERATURE = 0.98f;

    /**
     * 同步请求（答案不稳定）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncUnstableRequest(String systemMessage, String userMessage) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, UNSTABLE_TEMPERATURE);

    }

    /**
     * 同步请求（答案较稳定）
     *
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncStableRequest(String systemMessage, String userMessage) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, STABLE_TEMPERATURE);
    }

    /**
     * 同步请求
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String doSyncRequest(String systemMessage, String userMessage, Float temperature) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, temperature);
    }

    /**
     * 通用请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemMessage, String userMessage, Boolean stream, Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        chatMessageList.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doRequest(chatMessageList, stream, temperature);
    }

    /**
     * 通用请求
     *
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */

    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature) {
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model("glm-4")
                .messages(messages)
                .thinking(ChatThinking.builder().type("enabled").build())
                .maxTokens(3000)
                .stream(stream)
                .temperature(temperature)
                .build();

        // 发送请求
        ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);

        // 获取回复
        if (response.isSuccess()) {
            return response.getData().getChoices().get(0).getMessage().toString();
        } else {
            throw new RuntimeException("调用失败: " + response.getMsg());
        }

    }

    /**
     * 通用流式请求（简化消息传递）
     *
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(String systemMessage, String userMessage, Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        chatMessageList.add(systemChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doStreamRequest(chatMessageList, temperature);
    }

    /**
     * 通用流式请求
     *
     * @param messages
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(List<ChatMessage> messages, Float temperature) {
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model("glm-4")
                .messages(messages)
                .thinking(ChatThinking.builder().type("enabled").build())
                .maxTokens(3000)
                .stream(true)
                .temperature(temperature)
                .build();

        // 发送请求
        ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);

        // 获取回复
        if (!response.isSuccess() && response.getFlowable() != null) {
            throw new RuntimeException("流式调用失败: " + response.getMsg());
        }
        return response.getFlowable();
    }
}


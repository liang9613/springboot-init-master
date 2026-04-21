package com.yupi.liangdada;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;


@SpringBootTest
public class AiTest {


    @Resource
    private ZhipuAiClient zhipuAiClient;

    @Test
    public void test() {
        // 初始化客户端
//        ZhipuAiClient client = ZhipuAiClient.builder().ofZHIPU()
//                .apiKey("936917956c734191850ddc2ac6b83a5b.JdXeomidImTeOZxg")
//                .build();

        // 创建聊天完成请求
        ChatCompletionCreateParams request = ChatCompletionCreateParams.builder()
                .model("glm-4")
                .messages(Arrays.asList(
                        ChatMessage.builder()
                                .role(ChatMessageRole.USER.value())
                                .content("作为一名营销专家，请为我的产品创作一个吸引人的口号")
                                .build(),
                        ChatMessage.builder()
                                .role(ChatMessageRole.ASSISTANT.value())
                                .content("当然，要创作一个吸引人的口号，请告诉我一些关于您产品的信息")
                                .build()
                ))
                .thinking(ChatThinking.builder().type("enabled").build())
                .maxTokens(3000)
                .stream(true)
                .temperature(1.0f)
                .build();

        // 发送请求
        ChatCompletionResponse response = zhipuAiClient.chat().createChatCompletion(request);

        // 获取回复
        if (response.isSuccess() && response.getFlowable() != null) {
            response.getFlowable().blockingSubscribe(
                    data -> {
                        // 处理流式数据块
                        if (data.getChoices() != null && !data.getChoices().isEmpty()) {
                            Delta content = data.getChoices().get(0).getDelta();
                            System.out.print(content.getContent());
                        }
                    },
                    error -> System.err.println("\n 流式错误: " + error.getMessage()),
                    () -> System.out.println("\n 流式完成")
            );
        }
    }
}
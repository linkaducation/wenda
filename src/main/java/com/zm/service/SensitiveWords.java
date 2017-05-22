package com.zm.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ellen on 2017/5/22.
 */
@Service
public class SensitiveWords implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWords.class);

    private static final String DEFAULT_REPLACEMENT = "敏感词";

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private class triNode {
        /**
         * 关键词是否终结
         */
        private boolean isEnd = false;

        /**
         * key下一个字符，value是对应的节点
         */
        private Map<Character, triNode> subNodes = new HashMap<Character, triNode>();

        /**
         * 向指定位置添加节点
         */
        void addSubNode(Character key, triNode node) {
            subNodes.put(key, node);
        }

        /**
         * 获取下一个节点
         */
        triNode getTriNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeyWordEnd() {
            return isEnd;
        }

        void setKeyWordEnd(boolean isEnd) {
            this.isEnd = isEnd;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }
    }

    //根节点
    private triNode rootNode = new triNode();

    /**
     * 判断是否是一个符号
     */
    private boolean isSybol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    /**
     * 过滤敏感词
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        return null;
    }
}

-- 风险评估题库题目表
create table `risk_question`
(
  `id`               int(11)      NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `question_index`   VARCHAR(32)  NOT NULL COMMENT '题目序号',
  `content`          VARCHAR(512) NOT NULL COMMENT '题目内容',
  `status`           int(2)       NOT NULL COMMENT '题目状态(0.禁用 1.启用)',
  PRIMARY KEY (`id`)
);

-- 风险评估题库选项表
create table `risk_option`
(
  `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '选项ID',
  `question_id`  int(11)      NOT NULL COMMENT '题目ID',
  `option_index  VARCHAR(32)  NOT NULL COMMENT '选项序号',
  `content`      VARCHAR(512) NOT NULL COMMENT '选项内容',
  `score`        int(4)       NOT NULL COMMENT '选项分数',
  PRIMARY KEY (`id`),
  UNIQUE  KEY `uk_risk_question_id_index` (`question_id`, `index`) USING BTREE
);
-- 用户风险评估明细登记表
create table `risk_detail`
(
  `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `user_id`     int(11) NOT NULL COMMENT '用户ID',
  `question_id` int(11) NOT NULL COMMENT '题目ID',
  `option_id`   int(11) NOT NULL COMMENT '选项ID',
  PRIMARY KEY (`id`)
);
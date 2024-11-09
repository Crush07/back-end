package com.hysea.dept.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("me_student")
public class Student {

    @TableId("student_id")
    private Long studentId;

    @TableField("score")
    private Double score;

    @TableField("award_score")
    private Double awardScore;

    @TableField("punish_score")
    private Double punishScore;

}
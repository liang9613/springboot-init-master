package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.liangdada.model.entity.Question;
import generator.service.QuestionService;
import com.yupi.liangdada.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2026-03-29 17:40:43
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}





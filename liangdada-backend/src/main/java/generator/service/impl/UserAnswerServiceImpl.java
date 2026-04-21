package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.liangdada.model.entity.UserAnswer;
import generator.service.UserAnswerService;
import com.yupi.liangdada.mapper.UserAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service实现
* @createDate 2026-03-29 17:40:43
*/
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer>
    implements UserAnswerService{

}





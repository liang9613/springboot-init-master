package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.liangdada.model.entity.ScoringResult;
import generator.service.ScoringResultService;
import com.yupi.liangdada.mapper.ScoringResultMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【scoring_result(评分结果)】的数据库操作Service实现
* @createDate 2026-03-29 17:40:43
*/
@Service
public class ScoringResultServiceImpl extends ServiceImpl<ScoringResultMapper, ScoringResult>
    implements ScoringResultService{

}





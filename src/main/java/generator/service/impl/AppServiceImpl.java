package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.liangdada.model.entity.App;
import generator.service.AppService;
import com.yupi.liangdada.mapper.AppMapper;
import org.springframework.stereotype.Service;

/**
* @author liang
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2026-03-29 17:40:43
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{

}





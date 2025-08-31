package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.FairCountDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FairDao extends Dao{

    // 조회수별 박람회 조회
    public List<FairCountDto> fcountList(){
        List<FairCountDto> list = new ArrayList<>();
        try{
            String sql = " select fno , fname , fcount from fair order by fcount desc ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                FairCountDto dto = new FairCountDto();
                dto.setFno(rs.getInt("fno"));
                dto.setFname(rs.getString("fname"));
                dto.setFcount(rs.getInt("fcount"));
                list.add(dto);
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

    // 지역별 박람회 조회

    // 추천(알고리즘) 박람회 조회

}//class end

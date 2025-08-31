package web.model.dao;

import org.springframework.stereotype.Repository;
import web.model.dto.FairCountDto;
import web.model.dto.FairDto;

import javax.xml.transform.Result;
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

    // 지역별 그룹핑용 전체 박람회 조회
    public List<FairDto> selectAllFairs(){
        List<FairDto> list = new ArrayList<>();
        try {
            String sql = " select fno , fname , fplace from fair order by fplace , fno ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                FairDto dto = new FairDto();
                dto.setFno(rs.getInt("fno"));
                dto.setFname(rs.getString("fname"));
                dto.setFplace(rs.getString("fplace"));
                list.add(dto);
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e


    // --------------------------- 추천 알고리즘 ------------------------ //

    // fno 로 박람회 상세 조회
    public FairDto getFairbyFno(int fno){
        try {
            String sql = " select * from fair where fno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,fno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                FairDto dto = new FairDto();
                dto.setFno(rs.getInt("fno"));
                dto.setCno(rs.getInt("cno"));
                dto.setFname(rs.getString("fname"));
                dto.setFplace(rs.getString("fplace"));
                dto.setFprice(rs.getInt("fprice"));
                dto.setFurl(rs.getString("furl"));
                dto.setFinfo(rs.getString("finfo"));
                dto.setStart_date(rs.getString("start_date"));
                dto.setEnd_date(rs.getString("end_date"));
                dto.setFcount(rs.getInt("fcount"));
                return dto;
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return null;
    } // func e

    // 카테고리 기반 박람회 조회
    public List<FairDto> getFairsByCategory(int cno){
        List<FairDto> list = new ArrayList<>();
        try {
            String sql = " select * from fair where cno = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                FairDto dto = new FairDto();
                dto.setFno(rs.getInt("fno"));
                dto.setCno(rs.getInt("cno"));
                dto.setFname(rs.getString("fname"));
                dto.setFplace(rs.getString("fplace"));
                dto.setFprice(rs.getInt("fprice"));
                dto.setFurl(rs.getString("furl"));
                dto.setFinfo(rs.getString("finfo"));
                dto.setStart_date(rs.getString("start_date"));
                dto.setEnd_date(rs.getString("end_date"));
                dto.setFcount(rs.getInt("fcount"));
                list.add(dto);
            }
        }catch (Exception e ){
            System.out.println(e);
        }
        return list;
    } // func e

}//class end

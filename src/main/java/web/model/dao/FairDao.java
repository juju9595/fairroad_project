package web.model.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import web.model.dto.FairDto;
import web.model.dto.FairCountDto;
import web.model.dto.FairDto;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FairDao extends Dao{

    //박람회 등록
    public int fairWrite(FairDto fairDto){
        try{
            String sql = "INSERT INTO fair(fname , fimg , fplace , fprice , furl , finfo , start_date , end_date , fcount) values(?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,fairDto.getFname());
            ps.setString(2, fairDto.getFimg());
            ps.setString(3,fairDto.getFplace());
            ps.setInt(4,fairDto.getFprice());
            ps.setString(5,fairDto.getFurl());
            ps.setString(6,fairDto.getFinfo());
            ps.setString(7,fairDto.getStart_date());
            ps.setString(8,fairDto.getEnd_date());
            ps.setInt(9,fairDto.getFcount());
            int count = ps.executeUpdate();
            if(count==1)return 1;
            ps.close();
        } catch (Exception e) {System.out.println(e);}//catch end
        return 0;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 조회
    public List<FairDto>fairPrint(int cno,int startRow, int count){
        List<FairDto> list = new ArrayList<>();
        try{
            String sql = "SELECT *FROM fair f inner join category c ON f.cno = c.cno WHERE f.cno =? order by f.cno desc limit ?,?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cno);
            ps.setInt(2,startRow);
            ps.setInt(3,count);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                FairDto fairDto = new FairDto();
                fairDto.setFno(rs.getInt("fno"));
                fairDto.setFname(rs.getString("fname"));
                fairDto.setFimg(rs.getString("fimg"));
                fairDto.setFplace(rs.getString("fplace"));
                fairDto.setFprice(rs.getInt("fprice"));
                fairDto.setFurl(rs.getString("furl"));
                fairDto.setFinfo(rs.getString("finfo"));
                fairDto.setStart_date(rs.getString("start_date"));
                fairDto.setEnd_date(rs.getString("end_date"));
                fairDto.setFcount(rs.getInt("fcount"));
                list.add(fairDto);
            }//while end
            ps.close();
            rs.close();
        } catch (Exception e) {System.out.println("박람회조회"+e);}//catch end
        return list;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //게시물 개별 정보 조회
    public FairDto fairInfo(int fno){
        try{
            String sql = "SELECT *FROM fair WHERE fno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,fno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                FairDto fairDto = new FairDto();
                fairDto.setFno(rs.getInt("fno"));
                fairDto.setFname(rs.getString("fname"));
                fairDto.setFimg(rs.getString("fimg"));
                fairDto.setFplace(rs.getString("fplace"));
                fairDto.setFprice(rs.getInt("fprice"));
                fairDto.setFurl(rs.getString("furl"));
                fairDto.setFinfo(rs.getString("finfo"));
                fairDto.setStart_date(rs.getString("start_date"));
                fairDto.setEnd_date(rs.getString("end_date"));
                fairDto.setFcount(rs.getInt("fcount"));
                return fairDto;
            }//if end
            ps.close();
            rs.close();
        } catch (Exception e) {System.out.println("개별정보조회"+e);};//catch end
        return null;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    // 게시물 조회수 증가
    public void incrementCount(int fno){
        try{
            String sql = "UPDATE fair SET fcount = count + 1 WHERE fno=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,fno);
            ps.executeUpdate();
        } catch (Exception e) {System.out.println(e);}//catch end
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 카테고리별 게시물 수
    public int getTotalCount(int cno){
        try{
            String sql = "SELECT COUNT(*) FROM fair WHERE cno=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {System.out.println("카테고리별게시물수"+e);}//catch end
        return 0;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 카테고리별 게시물 수 [검색]
    public int getTotalCountSearch(int cno,String key, String keyword){
        try{
            String sql = "SELECT COUNT(*) FROM fair where cno=?";
            if(key.equals("fname")){sql+=" and fname like ? ";}
            else if(key.equals("finfo")){sql+= " and finfo like ? ";}
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cno);
            ps.setString(2,"%"+keyword+"%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }//if end

        }catch(Exception e){System.out.println("게시물수[검색]"+e);}//catch end
        return 0;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 카테고리별 게시물 전체 정보 조회 [검색]
    public List<FairDto>fairPrintSearch(int cno,int startRow,int count,String key, String keyword){
        List<FairDto> list =  new ArrayList<>();
        try{
           String sql = "select *from fair f inner join category c on f.cno = c.cno  where f.cno=?";
           if(key.equals("fname")){sql+=" and fname like ? ";}
           else if(key.equals("finfo")){sql+=" and finfo like ? ";}
           //페이징 처리
            sql += " order by fno desc limit ?,? ";
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setInt(1,cno);
           ps.setString(2,"%"+keyword+"%");
           ps.setInt(3,startRow);
           ps.setInt(4,count);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               FairDto fairDto = new FairDto();
               fairDto.setFno(rs.getInt("fno"));
               fairDto.setFname(rs.getString("fname"));
               fairDto.setFimg(rs.getString("fimg"));
               fairDto.setFplace(rs.getString("fplace"));
               fairDto.setFprice(rs.getInt("fprice"));
               fairDto.setFurl(rs.getString("furl"));
               fairDto.setFinfo(rs.getString("finfo"));
               fairDto.setStart_date(rs.getString("start_date"));
               fairDto.setEnd_date(rs.getString("end_date"));
               fairDto.setFcount(rs.getInt("fcount"));
               list.add(fairDto);
           }//while end
           ps.close();
           rs.close();
        }catch(Exception e){System.out.println("전체조회[검색]"+e);}//catch end
        return list;
    }//func end

    //----------------------------------------------------------------------------------------//

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

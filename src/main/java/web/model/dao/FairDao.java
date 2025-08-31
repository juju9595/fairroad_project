package web.model.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import web.model.dto.FairDto;

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
    public List<FairDto>fairPrint(){
        List<FairDto> list = new ArrayList<>();
        try{
            String sql = "SELECT *FROM fair;";
            PreparedStatement ps = conn.prepareStatement(sql);
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
        } catch (Exception e) {System.out.println(e);}//catch end
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
                fairDto.setFname(rs.getString("fanme"));
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
        } catch (Exception e) {System.out.println(e);};//catch end
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
            String sql = "SELECT COUNT(*) FROM category WHERE cno=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(cno);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {System.out.println(e);}//catch end
        return 0;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 카테고리별 게시물 수 [검색]
    public int getTotalCountSearch(int cno,String key, String keyword){
        try{
            String sql = "SELECT COUNT(*) FROM category where cno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }//if eend
            ps.close();
            rs.close();
        }catch(Exception e){System.out.println(e);}//catch end
        return 0;
    }//func end

    //-----------------------------------------------------------------------------------------------------------//

    //박람회 카테고리별 게시물 전체 정보 조회 [검색]
    public List<FairDto>fairPrint(int fno,int startRow,int count,String key, String keyword){
        List<FairDto> list =  new ArrayList<>();
        try{
           String sql = "select *from fair f inner join category c on f.cno = c.cno  where fno=?;";
           if(key.equals("ftitle")){sql+=" and ptiele like ? ";}
           else if(key.equals("finfo")){sql+=" and finfo like ? ";}
           //페이징 처리
            sql += " order by fno desc limit ?,? ";
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setInt(1,fno);
           ps.setString(2,"%"+keyword+"%");
           ps.setInt(3,startRow);
           ps.setInt(4,count);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               FairDto fairDto = new FairDto();
               fairDto.setFno(rs.getInt("fno"));
               fairDto.setFname(rs.getString("fanme"));
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
        }catch(Exception e){System.out.println(e);}//catch end
        return list;
    }//func end



}//class end

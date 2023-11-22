package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

class Repository_Test {

    UserRepository userRepository = new UserRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    HashtagRepository hashtagRepository = new HashtagRepository();
    BoardRepository boardRepository = new BoardRepository();
    BoardHashtagsRepository boardashtagsRepository = new BoardHashtagsRepository();

    @Test
    void create() {
        // 유저 생성
        User user1 = new User("xmaaks", "xmaaks@koreatech.ac.kr", "1234");
        User user2 = new User("조병하", "whqudgk@gmail.com", "4321");

        try {
            userRepository.createUser(user1);
            userRepository.createUser(user2);
        } catch (SQLException e){
            System.out.println("error1=" + e.getMessage());
        }

        // 카테고리 생성
        Categories categories_1 = new Categories("환영인사");
        Categories categories_2 = new Categories("일상 게시판");

        try {
            categoryRepository.CreateCategories(categories_1);
            categoryRepository.CreateCategories(categories_2);
        } catch (SQLException e){
            System.out.println("error2");
        }

        // 해시태그 생성
        ArrayList<Hashtags> hashtags = new ArrayList<>();

        hashtags.add(new Hashtags("인사"));
        hashtags.add(new Hashtags("환영"));
        hashtags.add(new Hashtags("과제"));
        hashtags.add(new Hashtags("ㅠㅠ"));

        for (Hashtags h: hashtags){
            try{
                h = hashtagRepository.CreateHashtags(h);
            } catch (SQLException e) {
                System.out.println("error3");
            }
        }

        // 게시글 생성
        Board newBoard1 = new Board("안녕하세요~", "BCSD Back_End 비기너 조병하 입니다~~", user1.getId(), categories_1.getId());
        Board newBoard2 = new Board("안녕하세욥욥욥~", "BCSD Back_End 비기너 코카콜라맛있다 입니다~~", user2.getId(), categories_2.getId());
        Board newBoard3 = new Board("나 아이패드 샀다~", "아니 아이패드 진짜 너무 비쌈 ㄷㄷ", user1.getId(), categories_2.getId());
        
        try {
            boardRepository.CreateBoard(newBoard1);
            boardRepository.CreateBoard(newBoard2);
            boardRepository.CreateBoard(newBoard3);
        } catch (SQLException e){
            System.out.println("error4");
        }

        // 게시글-해시태그 생성
        for(Hashtags h: hashtags){
            try {
                boardashtagsRepository.CreateBoardHashtags(new BoardHashtags(newBoard1.getId(), h.getId()));
                boardashtagsRepository.CreateBoardHashtags(new BoardHashtags(newBoard2.getId(), h.getId()));
            } catch (SQLException e){
                System.out.println("error5");
            }
        }
    }

    @Test
    void read(){
        // 모든 유저 불러오기
        ArrayList<User> user_list = null;
        try {
            user_list = userRepository.findAll();
        } catch (SQLException e){
            System.out.println("error1");
        }

        for(User u: user_list){
            System.out.println(u.toString());
        }

        // id로 특정 유저 불러오기
        User user_findById = null;
        try {
            user_findById = userRepository.findById(user_list.get(0).getId());
        } catch (SQLException e){
            System.out.println("error2");
        }

        System.out.println(user_findById.toString());

        // 모든 카테고리 불러오기
        ArrayList<Categories> categories_list = null;
        try {
            categories_list = categoryRepository.findAll();
        } catch (SQLException e) {
            System.out.println("error3");
        }

        for(Categories c: categories_list) {
            System.out.println(c.toString());
        }

        // id로 특정 카테고리 불러오기
        Categories category_findById = null;
        try {
            category_findById = categoryRepository.findbyId(categories_list.get(0).getId());
        } catch (SQLException e){
            System.out.println("error4");
        }

        System.out.println(category_findById.toString());

        // 모든 해시태그 불러오기
        ArrayList<Hashtags> hashtags_list = null;
        try {
            hashtags_list = hashtagRepository.findAll();
        } catch (SQLException e) {
            System.out.println("error5");
        }

        for(Hashtags h: hashtags_list) {
            System.out.println(h.toString());
        }

        // id로 특정 해시태그 불러오기
        Hashtags hashtag_findById = null;
        try {
            hashtag_findById = hashtagRepository.findbyId(hashtags_list.get(0).getId());
        } catch (SQLException e){
            System.out.println("error6");
        }

        System.out.println(hashtag_findById.toString());

        // 모든 게시글 불러오기
        ArrayList<Board> board_list = null;
        try {
            board_list = boardRepository.findAll();
        } catch (SQLException e) {
            System.out.println("error7");
        }

        for(Board b: board_list) {
            System.out.println(b.toString());
        }

        // 특정 id로 게시글 불러오기
        Board board_findById = null;
        try {
            board_findById = boardRepository.findbyId(board_list.get(0).getId());
        } catch (SQLException e) {
            System.out.println("error8");
        }

        System.out.println(board_findById.toString());

        // 모든 게시글-해시태그 아이디 불러오기
        ArrayList<BoardHashtags> BoardHashtags_list = null;
        try {
            BoardHashtags_list = boardashtagsRepository.findAll();
        } catch (SQLException e) {
            System.out.println("error9");
        }

        for(BoardHashtags bh: BoardHashtags_list) {
            System.out.println(bh.toString());
        }

        // 특정 board_id 아이디들 불러오기
        ArrayList<BoardHashtags> BoardHashtags_findByBoardId = null;
        try {
            BoardHashtags_findByBoardId = boardashtagsRepository.findbyBoardId(board_list.get(0).getId());
        } catch (SQLException e) {
            System.out.println("error10");
        }

        for(BoardHashtags bh: BoardHashtags_findByBoardId) {
            System.out.println(bh.toString());
        }

        // 특정 hashtag_id 아이디들 불러오기
        ArrayList<BoardHashtags> BoardHashtags_findByHashtagId = null;
        try {
            BoardHashtags_findByHashtagId = boardashtagsRepository.findbyHashtagId(hashtags_list.get(0).getId());
        } catch (SQLException e) {
            System.out.println("error11");
        }

        for(BoardHashtags bh: BoardHashtags_findByHashtagId) {
            System.out.println(bh.toString());
        }

    }

    @Test
    void update(){

        // 특정 카테고리 이름 업데이트
        try{
            categoryRepository.updateCategoryname(1, "그냥인사");
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 해시태그 이름 업데이트
        try{
            hashtagRepository.updateHashtagname(1, "코카콜라제로최고");
        } catch (SQLException e) {
            System.out.println("error2");
        }

        // 특정 게시글 제목 업데이트
        try{
            boardRepository.updateBoard(1, "사이다가 최고지", BoardRepository.UpdateField.TITLE);
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 게시글 내용 업데이트
        try{
            boardRepository.updateBoard(1, "무슨소리 ㅋㅋ 콜라가 짱이지 ㅋㅋ", BoardRepository.UpdateField.CONTENT);
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 게시글 카테고리 변경
        try{
            boardRepository.updateBoard(1, "2", BoardRepository.UpdateField.CATEGORY);
        } catch (SQLException e) {
            System.out.println("error1");
        }

    }

    @Test
    void delete(){

        // 특정 유저 제거
        try{
            userRepository.deleteUser(2);
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 카테고리 제거
        try{
            categoryRepository.DeleteCategory(1);
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 해시태그 제거
        try{
            hashtagRepository.DeleteHashtag(2);
        } catch (SQLException e) {
            System.out.println("error1");
        }

        // 특정 게시글 제거
        try{
            boardRepository.DeleteBoard(3);
        } catch (SQLException e) {
            System.out.println("error1");
        }

    }
}
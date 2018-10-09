package com.examnation.eduapp.model;

public class Chapter {


    public static final String TABLE_NAME_CHAPTER = "chapter";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VIDEO_COUNT = "videoCount";
    public static final String COLUMN_QUESTION_COUNT = "questionCount";
    public static final String COLUMN_CONCEPT_COUNT = "vconceptCount";
    public static final String COLUMN_SUBJECT_ID = "subjectId";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_SEQUENCE = "sequence";

    private Long id;
    private String name;
    private Integer videoCount;
    private Integer questionCount;
    private Integer conceptCount;
    private Long subjectId;
    private String classz;
    private Integer sequence;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_CHAPTER + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_VIDEO_COUNT + " INTEGER,"
                    + COLUMN_QUESTION_COUNT + " INTEGER,"
                    + COLUMN_CONCEPT_COUNT + " INTEGER,"
                    + COLUMN_SUBJECT_ID + " INTEGER,"
                    + COLUMN_CLASS + " TEXT,"
                    + COLUMN_SEQUENCE +" INTEGER"
                    + ")";

    public Chapter(){}

    public Chapter(Long id, String name, Integer videoCount, Integer questionCount, Integer conceptCount,Long subjectId,String classz,Integer sequence) {
        this.id = id;
        this.name = name;
        this.videoCount = videoCount;
        this.questionCount = questionCount;
        this.conceptCount = conceptCount;
        this.subjectId = subjectId;
        this.classz = classz;
        this.sequence = sequence;
    }

    public Chapter(Long id, String name, Integer videoCount, Integer questionCount, Integer conceptCount ) {
        this.id = id;
        this.name = name;
        this.videoCount = videoCount;
        this.questionCount = questionCount;
        this.conceptCount = conceptCount;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getConceptCount() {
        return conceptCount;
    }

    public void setConceptCount(Integer conceptCount) {
        this.conceptCount = conceptCount;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassz() {
        return classz;
    }

    public void setClassz(String classz) {
        this.classz = classz;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}

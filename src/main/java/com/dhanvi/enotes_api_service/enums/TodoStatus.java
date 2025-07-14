package com.dhanvi.enotes_api_service.enums;

public enum TodoStatus {
    Not_STARTED(1,"Not Started"),
    IN_PROGRESS(2,"In Progress"),
    COMPLETED(3,"Completed");

    private Integer id;
    private String name;

    TodoStatus(int i, String name) {
        this.id=i;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static TodoStatus fromCode(int code) {
        for (TodoStatus s : TodoStatus.values()) {
            if (s.id == code) return s;
        }
        throw new IllegalArgumentException("Invalid ID: " + code);
    }

    public void setName(String name) {
        this.name = name;
    }
}

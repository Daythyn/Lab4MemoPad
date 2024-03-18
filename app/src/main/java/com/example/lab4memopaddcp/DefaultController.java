package com.example.lab4memopaddcp;

public class DefaultController extends AbstractController {


    public static final String ELEMENT_ADDMEMO_PROPERTY = "AddMemo";
    public static final String ELEMENT_DELETEMEMO_PROPERTY = "DeleteMemo";
    public static final String ELEMENT_GETALLMEMOS_PROPERTY = "GetAllMemos";
    private final DefaultModel model;

    public DefaultController(DefaultModel model){
        super();
        this.model = model;
        addModel(model);
    }

    public void bAdd(String memo){
        model.setAddMemo(memo);
    }

    public void bDelete(int position){
        model.setDeleteMemo(position);
    }

    public void getAllMemos(){
        model.setGetAllMemos();
    }
}

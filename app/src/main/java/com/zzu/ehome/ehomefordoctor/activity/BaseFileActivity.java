package com.zzu.ehome.ehomefordoctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.mvp.presenter.BaseDataPresenter;
import com.zzu.ehome.ehomefordoctor.mvp.view.BaseDataView;
import com.zzu.ehome.ehomefordoctor.view.FlowLayout;
import com.zzu.ehome.ehomefordoctor.view.HeadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mersens on 2016/10/25.
 */

public class BaseFileActivity extends BaseActivity implements BaseDataView {

    @BindView(R.id.tv_maritalStatus)
    TextView tvMaritalStatus;
    @BindView(R.id.flowMedicineAllergy)
    FlowLayout flowMedicineAllergy;
    @BindView(R.id.flowPastMedicalHistory)
    FlowLayout flowPastMedicalHistory;
    @BindView(R.id.flowFamilyMedicalhistory_Father)
    FlowLayout flowFamilyMedicalhistoryFather;
    @BindView(R.id.flowFamilyMedicalhistory_Mother)
    FlowLayout flowFamilyMedicalhistoryMother;
    @BindView(R.id.flowFamilyMedicalhistory_Sister)
    FlowLayout flowFamilyMedicalhistorySister;
    @BindView(R.id.flowFamilyMedicalhistory_Children)
    FlowLayout flowFamilyMedicalhistoryChildren;
    @BindView(R.id.tv_blood_type)
    TextView tvBloodType;
    @BindView(R.id.flow_GeneticHistory)
    FlowLayout flowGeneticHistory;
    @BindView(R.id.tv_smokeState)
    TextView tvSmokeState;
    @BindView(R.id.tv_drinkState)
    TextView tvDrinkState;

    private String maritalStatusNames = "";//婚姻状况
    private String medicineAllergyNames = "";//药物过敏
    private String pastMedicalHistoryNames = "";//既往病史
    private String familyNames = "";
    private String geneticHistoryNmaes = "";//遗传病史
    private String drinkStateNames = "";//喝酒状况
    private String smokeStateNames = "";//吸烟状况
    private String bloodtype="";
    private String familyMedicalhistory_fatherNames = "";//家族病史（父亲）
    private String familyMedicalhistory_motherNames = "";//家族病史（母亲）
    private String familyMedicalhistory_sisterNames = "";//家族病史（兄弟姐妹）
    private String familyMedicalhistory_childrenNames = "";//家族病史（子女）
    private ViewGroup.MarginLayoutParams lp;
    private String mTargetId;
    private BaseDataPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_file);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        mTargetId=getIntent().getStringExtra(MedicalRecordActivity.TARGETID);
        setLeftWithTitleViewMethod(R.mipmap.icon_arrow_left, "基础档案", new HeadView.OnLeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        lp = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        lp.bottomMargin = 8;
        lp.rightMargin = 12;
        lp.topMargin = 8;
        presenter=new BaseDataPresenter(this);
        presenter.getBaseData();

    }

    @Override
    public <T> void onSuccess(T t) {

        try {
            JSONObject mySO = new JSONObject((String)t);
            JSONArray json = mySO.getJSONArray("BaseDataInquiry");
            if (!json.getJSONObject(0).has("MessageCode")) {
                JSONObject jsonobject = json.getJSONObject(0);
                maritalStatusNames = jsonobject.getString("Marriage");
                smokeStateNames = jsonobject.getString("Smoking");
                drinkStateNames = jsonobject.getString("Drinking");
                medicineAllergyNames = jsonobject.getString("DrugAllergy");
                pastMedicalHistoryNames = jsonobject.getString("MedicalHistory");
                geneticHistoryNmaes = jsonobject.getString("GeneticHistory");
                familyNames = jsonobject.getString("FamilyHistory");
                bloodtype= jsonobject.getString("BloodType");
                clearFlowData();
                setDatas();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserId() {
        return mTargetId;
    }

    public void setDatas() {

        if(!TextUtils.isEmpty(bloodtype)){
            tvBloodType.setText(bloodtype);
        }
        if (!TextUtils.isEmpty(maritalStatusNames)) {
            tvMaritalStatus.setText(maritalStatusNames);
        }

        if (!TextUtils.isEmpty(medicineAllergyNames)) {
            String names[] = medicineAllergyNames.split(",");

            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {

                    flowMedicineAllergy.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowMedicineAllergy.addView(getTextView("无"), lp);
        }
        if (!TextUtils.isEmpty(pastMedicalHistoryNames)) {
            String names[] = pastMedicalHistoryNames.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowPastMedicalHistory.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowPastMedicalHistory.addView(getTextView("无"), lp);
        }
        String nametypes[] = familyNames.split(",");
        for (int i = 0; i < nametypes.length; i++) {
            if (nametypes[i].contains(":")) {
                String names[] = nametypes[i].split(":");
                if ("父亲".equals(names[0])) {
                    familyMedicalhistory_fatherNames = familyMedicalhistory_fatherNames + names[1] + ",";
                } else if ("母亲".equals(names[0])) {
                    familyMedicalhistory_motherNames = familyMedicalhistory_motherNames + names[1] + ",";

                } else if ("兄弟姐妹".equals(names[0])) {
                    familyMedicalhistory_sisterNames = familyMedicalhistory_sisterNames + names[1] + ",";

                } else if ("子女".equals(names[0])) {
                    familyMedicalhistory_childrenNames = familyMedicalhistory_childrenNames + names[1] + ",";

                }
            }

        }
        if (!TextUtils.isEmpty(familyMedicalhistory_fatherNames)) {
            String names[] = familyMedicalhistory_fatherNames.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowFamilyMedicalhistoryFather.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowFamilyMedicalhistoryFather.addView(getTextView("无"), lp);
        }

        if (!TextUtils.isEmpty(familyMedicalhistory_motherNames)) {
            String names[] = familyMedicalhistory_motherNames.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowFamilyMedicalhistoryMother.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowFamilyMedicalhistoryMother.addView(getTextView("无"), lp);
        }

        if (!TextUtils.isEmpty(familyMedicalhistory_sisterNames)) {
            String names[] = familyMedicalhistory_sisterNames.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowFamilyMedicalhistorySister.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowFamilyMedicalhistorySister.addView(getTextView("无"), lp);
        }

        if (!TextUtils.isEmpty(familyMedicalhistory_childrenNames)) {
            String names[] = familyMedicalhistory_childrenNames.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowFamilyMedicalhistoryChildren.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowFamilyMedicalhistoryChildren.addView(getTextView("无"), lp);
        }

        if (!TextUtils.isEmpty(geneticHistoryNmaes)) {
            String names[] = geneticHistoryNmaes.split(",");
            for (int i = 0; i < names.length; i++) {
                if (!TextUtils.isEmpty(names[i]) && !" ".equals(names[i])) {
                    flowGeneticHistory.addView(getTextView(names[i]), lp);
                }
            }
        } else {
            flowGeneticHistory.addView(getTextView("无"), lp);
        }

        if (!TextUtils.isEmpty(drinkStateNames)) {
            tvDrinkState.setText(drinkStateNames);
        }
        if (!TextUtils.isEmpty(smokeStateNames)) {
            tvSmokeState.setText(smokeStateNames);
        }
    }


    public void clearFlowData() {
        familyMedicalhistory_fatherNames = "";//家族病史（父亲）
        familyMedicalhistory_motherNames = "";//家族病史（母亲）
        familyMedicalhistory_sisterNames = "";//家族病史（兄弟姐妹）
        familyMedicalhistory_childrenNames = "";//家族病史（子女）
        flowFamilyMedicalhistoryFather.removeAllViews();
        flowFamilyMedicalhistoryMother.removeAllViews();
        flowFamilyMedicalhistorySister.removeAllViews();
        flowFamilyMedicalhistoryChildren.removeAllViews();
        flowMedicineAllergy.removeAllViews();
        flowPastMedicalHistory.removeAllViews();
        flowGeneticHistory.removeAllViews();
    }

    private TextView getTextView(String text) {
        TextView tv = new TextView(BaseFileActivity.this);
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(getResources().getColor(R.color.text_color1));
        return tv;
    }
}

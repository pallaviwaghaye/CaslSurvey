package com.casl.Utility;

import com.casl.caslsurvey.R;

public class MappingData {
    public String getTypeOfContact(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "In-person":
                mappedStr = "inper";
                break;

            case "Telephone":
                mappedStr = "telephone";

                break;

            case "Letter":
                mappedStr = "letter";
                break;
        }


        return mappedStr;
    }

    public String getPurposeOfContact(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "Request participation in a study activity":
                mappedStr = "01";
                break;

            case "Schedule or confirm study appointment":
                mappedStr = "02";

                break;
            case "Collect study data":
                mappedStr = "03";

                break;


            case "Provide study information/Answer subject or family study questions":
                mappedStr = "04";
                break;

            case "Contact requested or initiated by subject":
                mappedStr = "05";
                break;

            case "Appointment cancellation":
                mappedStr = "09";
                break;

            case "Gather subjects contact information from family members/trusted others":
                mappedStr = "10";
                break;
        }


        return mappedStr;
    }

    public String getIntMethod(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "In-person":
                mappedStr = "inperson";
                break;

            case "Phone":
                mappedStr = "phone";

                break;

            case "Mailed-in":
                mappedStr = "mail";
                break;

            case "Online":
                mappedStr = "online";
                break;
        }


        return mappedStr;
    }

    public String getNoContactMethod(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "Not at home":
                mappedStr = "outOfHome";
                break;

            case "Wrong telephone number":
                mappedStr = "wrongPhoneNumber";

                break;

            case "Nobody pick up phone":
                mappedStr = "nobodypickup";
                break;

            case "Not able to answer":
                mappedStr = "notAbleToAnswer";
                break;

            case "Moved to other parts of city":
                mappedStr = "movedOtherParts";
                break;

            case "Moved to suburbs":
                mappedStr = "movedSuburbs";
                break;

            case "Moved out of state":
                mappedStr = "movedOutState";
                break;

            case "Moved out of country":
                mappedStr = "movedOutCountry";
                break;

            case "Moved unknown":
                mappedStr = "movedUnknown";
                break;

            case "Other":
                mappedStr = "other";
                break;
        }


        return mappedStr;
    }

    public String getDieCause(String selected){
        String mappedStr = "";

        switch (selected) {

            case "Heart Disease":
                mappedStr = "hea";
                break;

            case "Cancer":
                mappedStr = "can";

                break;

            case "Cerebrovascular Disease":
                mappedStr = "cer";
                break;

            case "Lung Disease":
                mappedStr = "lun";
                break;

            case "Kidney Disease":
                mappedStr = "kid";
                break;

            case "Accident":
                mappedStr = "acc";

                break;

            case "Infection":
                mappedStr = "inf";
                break;

            case "Other":
                mappedStr = "oth";
                break;

        }

        return mappedStr;
    }

    public String getDiePlace(String selected){
        String mappedStr = "";

        switch (selected) {

            case "Own home":
                mappedStr = "home";
                break;

            case "Family members home":
                mappedStr = "famhome";

                break;

            case "Nursing home":
                mappedStr = "nursing";
                break;

            case "Hospital":
                mappedStr = "hospital";
                break;

            case "Emergency Room":
                mappedStr = "emergroom";
                break;

            case "Rehab center":
                mappedStr = "rehabcen";

                break;

            case "Other":
                mappedStr = "other";
                break;

        }

        return mappedStr;
    }

    public String getDieState(String selected){
        String mappedStr = "";

        switch (selected) {

            case "Alabama":
                mappedStr = "AL";
                break;
            case "Alaska":
                mappedStr = "AK";
                break;
            case "Arizona":
                mappedStr = "AZ";
                break;
            case "Arkansas":
                mappedStr = "AR";
                break;
            case "California":
                mappedStr = "CA";
                break;
            case "Colorado":
                mappedStr = "CO";
                break;
            case "Connecticut":
                mappedStr = "CT";
                break;

            case "Delaware":
                mappedStr = "DE";
                break;
            case "Florida":
                mappedStr = "FL";
                break;
            case "Georgia":
                mappedStr = "GA";
                break;
            case "Hawaii":
                mappedStr = "HI";
                break;
            case "Idaho":
                mappedStr = "ID";
                break;
            case "Illinois":
                mappedStr = "IL";
                break;
            case "Indiana":
                mappedStr = "IN";
                break;

            case "Iowa":
                mappedStr = "IA";
                break;
            case "Kansas":
                mappedStr = "KS";
                break;
            case "Kentucky":
                mappedStr = "KY";
                break;
            case "Louisiana":
                mappedStr = "LA";
                break;
            case "Maine":
                mappedStr = "ME";
                break;
            case "Maryland":
                mappedStr = "MD";
                break;
            case "Massachusetts":
                mappedStr = "MA";
                break;

            case "Michigan":
                mappedStr = "MI";
                break;
            case "Minnesota":
                mappedStr = "MN";
                break;
            case "Mississippi":
                mappedStr = "MS";
                break;
            case "Missouri":
                mappedStr = "MO";
                break;
            case "Montana":
                mappedStr = "MT";
                break;
            case "Nebraska":
                mappedStr = "NE";
                break;
            case "Nevada":
                mappedStr = "NV";
                break;

            case "New Hampshire":
                mappedStr = "NH";
                break;
            case "New Jersey":
                mappedStr = "NJ";
                break;
            case "New Mexico":
                mappedStr = "NM";
                break;
            case "New York":
                mappedStr = "NY";
                break;
            case "North Carolina":
                mappedStr = "NC";
                break;
            case "North Dakota":
                mappedStr = "ND";
                break;
            case "Ohio":
                mappedStr = "OH";
                break;

            case "Oklahoma":
                mappedStr = "OK";
                break;
            case "Oregon":
                mappedStr = "OR";
                break;
            case "Pennsylvania":
                mappedStr = "PA";
                break;
            case "Rhode Island":
                mappedStr = "RI";
                break;
            case "South Carolina":
                mappedStr = "SC";
                break;
            case "South Dakota":
                mappedStr = "SD";
                break;
            case "Tennessee":
                mappedStr = "TN";
                break;

            case "Texas":
                mappedStr = "TX";
                break;
            case "Utah":
                mappedStr = "UT";
                break;
            case "Vermont":
                mappedStr = "VT";
                break;
            case "Virginia":
                mappedStr = "VA";
                break;
            case "Washington":
                mappedStr = "WA";
                break;
            case "West Virginia":
                mappedStr = "WV";
                break;
            case "Wisconsin":
                mappedStr = "WI";
                break;

            case "Wyoming":
                mappedStr = "WY";
                break;
            case "Out of Country":
                mappedStr = "OutofCountry";
                break;

        }

        return mappedStr;
    }

    public String getDieWhome(String selected){
        String mappedStr = "";

        switch (selected) {

            case "Spouse":
                mappedStr = "spouse";
                break;
            case "Ex-spouse":
                mappedStr = "exspouse";
                break;
            case "Romantic partner":
                mappedStr = "partner";
                break;
            case "Son":
                mappedStr = "son";
                break;
            case "Daughter":
                mappedStr = "daughter";
                break;
            case "Son-in-law":
                mappedStr = "soninlaw";
                break;
            case "Daughter-in-law":
                mappedStr = "daughterinlaw";
                break;


            case "Step-child":
                mappedStr = "stepchild";
                break;
            case "Grandson":
                mappedStr = "grandson";
                break;
            case "Granddaughter":
                mappedStr = "granddaughter";
                break;
            case "Father":
                mappedStr = "father";
                break;
            case "Mother":
                mappedStr = "mother";
                break;
            case "Father-in-law":
                mappedStr = "fatherinlaw";
                break;
            case "Mother-in-law":
                mappedStr = "mother-in-law";
                break;

            case "Brother":
                mappedStr = "brother";
                break;
            case "Sister":
                mappedStr = "sister";
                break;
            case "Other family member":
                mappedStr = "otherfamily";
                break;
            case "Trusted friend":
                mappedStr = "trustedfriend";
                break;
            case "Other trusted person":
                mappedStr = "otherperson";
                break;
            case "Neighbor":
                mappedStr = "neighbor";
                break;
            case "Caretaker from outside":
                mappedStr = "caretakeroutside";
                break;

            case "Caretaker from nursing home":
                mappedStr = "nursinghome";
                break;
            case "Banker":
                mappedStr = "banker";
                break;
            case "Lawyer":
                mappedStr = "lawyer";
                break;
            case "City or State worker":
                mappedStr = "cityworker";
                break;
            case "Salesman":
                mappedStr = "salesman";
                break;
            case "Contractor to fix house":
                mappedStr = "contractor";
                break;
            case "Other":
                mappedStr = "other";
                break;
        }

        return mappedStr;
    }

    public String getPartRel2_FamRel2(String selected){
        String mappedStr = "";

        switch (selected) {

            case "Mild unspoken reluctance":
                mappedStr = "mild1";
                break;

            case "Mild overt reluctance":
                mappedStr = "mild2";

                break;

            case "Moderate reluctance":
                mappedStr = "moder";
                break;

            case "Strong reluctance":
                mappedStr = "strong1";
                break;

            case "Strong reluctance with hostile comments":
                mappedStr = "strong2";
                break;

        }

        return mappedStr;
    }

    public String get_OthrContact_reluctance_partrel1_famrel1_famrel4(String selected) {

        String mappedStr = "";

        switch (selected) {

            //case R.string.yes:
            case "Yes":
                mappedStr = "yes";
                break;

            case "No":
                mappedStr = "no";
                break;

        }

        return mappedStr;
    }

    public String get_Death_NursHome(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "Yes":
                mappedStr = "yes";
                break;
            case "No":
                mappedStr = "no";
                break;
            case "Dont know":
                mappedStr = "dontknow";
                break;
            case "Refusal":
                mappedStr = "refusal";
                break;
        }

        return mappedStr;
    }

    /*public static String getNursHome(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "Yes":
                mappedStr = "yes";
                break;
            case "No":
                mappedStr = "no";
                break;
            case "Dont know":
                mappedStr = "dontknow";
                break;
            case "Refusal":
                mappedStr = "refusal";
                break;
        }

        return mappedStr;
    }*/
}

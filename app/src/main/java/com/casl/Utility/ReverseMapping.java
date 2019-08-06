package com.casl.Utility;

public class ReverseMapping {

    public String getTypeOfContact(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "inper":
                mappedStr = "In-person";
                break;

            case "telephone":
                mappedStr = "Telephone";

                break;

            case "letter":
                mappedStr = "Letter";
                break;
        }


        return mappedStr;
    }

    public String getPurposeOfContact(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "01":
                mappedStr = "Request participation in a study activity";
                break;

            case "02":
                mappedStr = "Schedule or confirm study appointment";

                break;
            case "03":
                mappedStr = "Collect study data";

                break;


            case "04":
                mappedStr = "Provide study information/Answer subject or family study questions";
                break;

            case "05":
                mappedStr = "Contact requested or initiated by subject";
                break;

            case "09":
                mappedStr = "Appointment cancellation";
                break;

            case "10":
                mappedStr = "Gather subjects contact information from family members/trusted others";
                break;
        }


        return mappedStr;
    }

    public String getIntMethod(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "inperson":
                mappedStr = "In-person";
                break;

            case "phone":
                mappedStr = "Phone";

                break;

            case "mail":
                mappedStr = "Mailed-in";
                break;

            case "online":
                mappedStr = "Online";
                break;
        }


        return mappedStr;
    }

    public String getNoContactMethod(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "outOfHome":
                mappedStr = "Not at home";
                break;

            case "wrongPhoneNumber":
                mappedStr = "Wrong telephone number";

                break;

            case "nobodypickup":
                mappedStr = "Nobody pick up phone";
                break;

            case "notAbleToAnswer":
                mappedStr = "Not able to answer";
                break;

            case "movedOtherParts":
                mappedStr = "Moved to other parts of city";
                break;

            case "movedSuburbs":
                mappedStr = "Moved to suburbs";
                break;

            case "movedOutState":
                mappedStr = "Moved out of state";
                break;

            case "movedOutCountry":
                mappedStr = "Moved out of country";
                break;

            case "movedUnknown":
                mappedStr = "Moved unknown";
                break;

            case "other":
                mappedStr = "Other";
                break;
        }


        return mappedStr;
    }

    public String getDieCause(String selected) {
        String mappedStr = "";

        switch (selected) {

            case "hea":
                mappedStr = "Heart Disease";
                break;

            case "can":
                mappedStr = "Cancer";

                break;

            case "cer":
                mappedStr = "Cerebrovascular Disease";
                break;

            case "lun":
                mappedStr = "Lung Disease";
                break;

            case "kid":
                mappedStr = "Kidney Disease";
                break;

            case "acc":
                mappedStr = "Accident";

                break;

            case "inf":
                mappedStr = "Infection";
                break;

            case "oth":
                mappedStr = "Other";
                break;

        }

        return mappedStr;
    }

    public String getDiePlace(String selected) {
        String mappedStr = "";

        switch (selected) {

            case "home":
                mappedStr = "Own home";
                break;

            case "famhome":
                mappedStr = "Family members home";

                break;

            case "nursing":
                mappedStr = "Nursing home";
                break;

            case "hospital":
                mappedStr = "Hospital";
                break;

            case "emergroom":
                mappedStr = "Emergency Room";
                break;

            case "rehabcen":
                mappedStr = "Rehab center";

                break;

            case "other":
                mappedStr = "Other";
                break;

        }

        return mappedStr;
    }

    public String getDieState(String selected){
        String mappedStr = "";

        switch (selected) {

            case "AL":
                mappedStr = "Alabama";
                break;
            case "AK":
                mappedStr = "Alaska";
                break;
            case "AZ":
                mappedStr = "Arizona";
                break;
            case "AR":
                mappedStr = "Arkansas";
                break;
            case "CA":
                mappedStr = "California";
                break;
            case "CO":
                mappedStr = "Colorado";
                break;
            case "CT":
                mappedStr = "Connecticut";
                break;

            case "DE":
                mappedStr = "Delaware";
                break;
            case "FL":
                mappedStr = "Florida";
                break;
            case "GA":
                mappedStr = "Georgia";
                break;
            case "HI":
                mappedStr = "Hawaii";
                break;
            case "ID":
                mappedStr = "Idaho";
                break;
            case "IL":
                mappedStr = "Illinois";
                break;
            case "IN":
                mappedStr = "Indiana";
                break;

            case "IA":
                mappedStr = "Iowa";
                break;
            case "KS":
                mappedStr = "Kansas";
                break;
            case "KY":
                mappedStr = "Kentucky";
                break;
            case "LA":
                mappedStr = "Louisiana";
                break;
            case "ME":
                mappedStr = "Maine";
                break;
            case "MD":
                mappedStr = "Maryland";
                break;
            case "MA":
                mappedStr = "Massachusetts";
                break;

            case "MI":
                mappedStr = "Michigan";
                break;
            case "MN":
                mappedStr = "Minnesota";
                break;
            case "MS":
                mappedStr = "Mississippi";
                break;
            case "MO":
                mappedStr = "Missouri";
                break;
            case "MT":
                mappedStr = "Montana";
                break;
            case "NE":
                mappedStr = "Nebraska";
                break;
            case "NV":
                mappedStr = "Nevada";
                break;

            case "NH":
                mappedStr = "New Hampshire";
                break;
            case "NJ":
                mappedStr = "New Jersey";
                break;
            case "NM":
                mappedStr = "New Mexico";
                break;
            case "NY":
                mappedStr = "New York";
                break;
            case "NC":
                mappedStr = "North Carolina";
                break;
            case "ND":
                mappedStr = "North Dakota";
                break;
            case "OH":
                mappedStr = "Ohio";
                break;

            case "OK":
                mappedStr = "Oklahoma";
                break;
            case "OR":
                mappedStr = "Oregon";
                break;
            case "PA":
                mappedStr = "Pennsylvania";
                break;
            case "RI":
                mappedStr = "Rhode Island";
                break;
            case "SC":
                mappedStr = "South Carolina";
                break;
            case "SD":
                mappedStr = "South Dakota";
                break;
            case "TN":
                mappedStr = "Tennessee";
                break;

            case "TX":
                mappedStr = "Texas";
                break;
            case "UT":
                mappedStr = "Utah";
                break;
            case "VT":
                mappedStr = "Vermont";
                break;
            case "VA":
                mappedStr = "Virginia";
                break;
            case "WA":
                mappedStr = "Washington";
                break;
            case "WV":
                mappedStr = "West Virginia";
                break;
            case "WI":
                mappedStr = "Wisconsin";
                break;

            case "WY":
                mappedStr = "Wyoming";
                break;
            case "OutofCountry":
                mappedStr = "Out of Country";
                break;

        }

        return mappedStr;
    }

    public String getDieWhome(String selected) {
        String mappedStr = "";

        switch (selected) {

            case "spouse":
                mappedStr = "Spouse";
                break;
            case "Ex-spouse":
                mappedStr = "exspouse";
                break;
            case "partner":
                mappedStr = "Romantic partner";
                break;
            case "son":
                mappedStr = "Son";
                break;
            case "daughter":
                mappedStr = "Daughter";
                break;
            case "soninlaw":
                mappedStr = "Son-in-law";
                break;
            case "daughterinlaw":
                mappedStr = "Daughter-in-law";
                break;


            case "stepchild":
                mappedStr = "Step-child";
                break;
            case "grandson":
                mappedStr = "Grandson";
                break;
            case "granddaughter":
                mappedStr = "Granddaughter";
                break;
            case "father":
                mappedStr = "Father";
                break;
            case "mother":
                mappedStr = "Mother";
                break;
            case "fatherinlaw":
                mappedStr = "Father-in-law";
                break;
            case "mother-in-law":
                mappedStr = "Mother-in-law";
                break;

            case "brother":
                mappedStr = "Brother";
                break;
            case "sister":
                mappedStr = "Sister";
                break;
            case "otherfamily":
                mappedStr = "Other family member";
                break;
            case "trustedfriend":
                mappedStr = "Trusted friend";
                break;

            case "otherperson":
                mappedStr = "Other trusted person";
                break;

            case "neighbor":
                mappedStr = "Neighbor";
                break;
            case "caretakeroutside":
                mappedStr = "Caretaker from outside";
                break;

            case "nursinghome":
                mappedStr = "Caretaker from nursing home";
                break;
            case "banker":
                mappedStr = "Banker";
                break;
            case "lawyer":
                mappedStr = "Lawyer";
                break;
            case "cityworker":
                mappedStr = "City or State worker";
                break;
            case "salesman":
                mappedStr = "Salesman";
                break;
            case "contractor":
                mappedStr = "Contractor to fix house";
                break;
            case "other":
                mappedStr = "Other";
                break;
        }

        return mappedStr;
    }

    public String getPartRel2_FamRel2(String selected) {
        String mappedStr = "";

        switch (selected) {

            case "mild1":
                mappedStr = "Mild unspoken reluctance";
                break;

            case "mild2":
                mappedStr = "Mild overt reluctance";

                break;

            case "moder":
                mappedStr = "Moderate reluctance";
                break;

            case "strong1":
                mappedStr = "Strong reluctance";
                break;

            case "strong2":
                mappedStr = "Strong reluctance with hostile comments";
                break;

        }

        return mappedStr;
    }

    public String getYesNo(String selected) {

        String mappedStr = "";

        switch (selected) {

            //case R.string.yes:
            case "yes":
                mappedStr = "Yes";
                break;

            case "no":
                mappedStr = "No";
                break;

        }

        return mappedStr;
    }

    public String get_Death_NursHome(String selected) {

        String mappedStr = "";

        switch (selected) {

            case "yes":
                mappedStr = "Yes";
                break;
            case "no":
                mappedStr = "No";
                break;
            case "dontknow":
                mappedStr = "Dont know";
                break;
            case "refusal":
                mappedStr = "Refusal";
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

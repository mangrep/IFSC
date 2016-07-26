package in.co.techm.ifsc;

/**
 * Created by turing on 9/4/16.
 */
public interface Constants {
    String BASE_API_URL = "http://192.168.0.10:3000/api/";
    String BANK_DETAILS = "bank_details";

    interface REST_ENDPOINTS {
        String API_BANK_LIST = "listbanks";
        String API_BRANCH_LIST = "listbranches";
        String API_BANK_DETAILS = "getbank";
    }

    interface ERROR_MESSAGE {
        String SOMETHING_WENT_WRONG = "Something went wrong";
        String UNABLE_TO_LOAD_BANK_LIST = "Unable to load bank list";
    }

    interface BANK_LIST {
        String[] STORED_BANK_LIST = {"ABHYUDAYA COOPERATIVE BANK LIMITED", "ABU DHABI COMMERCIAL BANK", "AHMEDABAD MERCANTILE COOPERATIVE BANK", "AKOLA JANATA COMMERCIAL COOPERATIVE BANK", "ALLAHABAD BANK", "ALMORA URBAN COOPERATIVE BANK LIMITED", "ANDHRA BANK", "ANDHRA PRAGATHI GRAMEENA BANK", "APNA SAHAKARI BANK LIMITED", "AUSTRALIA AND NEW ZEALAND BANKING GROUP LIMITED", "AXIS BANK", "B N P PARIBAS", "BANDHAN BANK LIMITED", "BANK INTERNASIONAL INDONESIA", "BANK OF AMERICA", "BANK OF BAHARAIN AND KUWAIT BSC", "BANK OF BARODA", "BANK OF CEYLON", "BANK OF INDIA", "BANK OF MAHARASHTRA", "BANK OF TOKYO MITSUBISHI LIMITED", "BARCLAYS BANK", "BASSEIN CATHOLIC COOPERATIVE BANK LIMITED", "BHARAT COOPERATIVE BANK MUMBAI LIMITED", "BHARATIYA MAHILA BANK LIMITED", "CANARA BANK", "CAPITAL LOCAL AREA BANK LIMITED", "CATHOLIC SYRIAN BANK LIMITED", "CENTRAL BANK OF INDIA", "CHINATRUST COMMERCIAL BANK LIMITED", "CITI BANK", "CITIZEN CREDIT COOPERATIVE BANK LIMITED", "CITY UNION BANK LIMITED", "COMMONWEALTH BANK OF AUSTRALIA", "CORPORATION BANK", "CREDIT AGRICOLE CORPORATE AND INVESTMENT BANK CALYON BANK", "CREDIT SUISEE AG", "DCB BANK LIMITED", "DENA BANK", "DEPOSIT INSURANCE AND CREDIT GUARANTEE CORPORATION", "DEUSTCHE BANK", "DEVELOPMENT BANK OF SINGAPORE", "DHANALAKSHMI BANK", "DOHA BANK", "DOHA BANK QSC", "DOMBIVLI NAGARI SAHAKARI BANK LIMITED", "EXPORT IMPORT BANK OF INDIA", "FEDERAL BANK", "FIRSTRAND BANK LIMITED", "G P PARSIK BANK", "GURGAON GRAMIN BANK", "HDFC BANK", "HSBC BANK", "HSBC BANK OMAN SAOG", "ICICI BANK LIMITED", "IDBI BANK", "IDFC BANK LIMITED", "INDIAN BANK", "INDIAN OVERSEAS BANK", "INDUSIND BANK", "INDUSTRIAL AND COMMERCIAL BANK OF CHINA LIMITED", "INDUSTRIAL BANK OF KOREA", "ING VYSYA BANK", "JALGAON JANATA SAHAKARI BANK LIMITED", "JAMMU AND KASHMIR BANK LIMITED", "JANAKALYAN SAHAKARI BANK LIMITED", "JANASEVA SAHAKARI BANK BORIVLI LIMITED", "JANASEVA SAHAKARI BANK LIMITED", "JANATA SAHAKARI BANK LIMITED", "JP MORGAN BANK", "KALLAPPANNA AWADE ICHALKARANJI JANATA SAHAKARI BANK LIMITED", "KALUPUR COMMERCIAL COOPERATIVE BANK", "KALYAN JANATA SAHAKARI BANK", "KAPOL COOPERATIVE BANK LIMITED", "KARNATAKA BANK LIMITED", "KARNATAKA VIKAS GRAMEENA BANK", "KARUR VYSYA BANK", "KERALA GRAMIN BANK", "KOTAK MAHINDRA BANK LIMITED", "LAXMI VILAS BANK", "MAHANAGAR COOPERATIVE BANK", "MAHARASHTRA STATE COOPERATIVE BANK", "MASHREQBANK PSC", "MIZUHO CORPORATE BANK LIMITED", "NAGAR URBAN CO OPERATIVE BANK", "NAGPUR NAGARIK SAHAKARI BANK LIMITED", "NATIONAL AUSTRALIA BANK LIMITED", "NATIONAL BANK OF ABU DHABI PJSC", "NEW INDIA COOPERATIVE BANK LIMITED", "NKGSB COOPERATIVE BANK LIMITED", "NUTAN NAGARIK SAHAKARI BANK LIMITED", "OMAN INTERNATIONAL BANK SAOG", "ORIENTAL BANK OF COMMERCE", "PRAGATHI KRISHNA GRAMIN BANK", "PRATHAMA BANK", "PRIME COOPERATIVE BANK LIMITED", "PUNJAB AND MAHARSHTRA COOPERATIVE BANK", "PUNJAB AND SIND BANK", "PUNJAB NATIONAL BANK", "RABOBANK INTERNATIONAL", "RAJGURUNAGAR SAHAKARI BANK LIMITED", "RAJKOT NAGRIK SAHAKARI BANK LIMITED", "RBL BANK LIMITED", "RESERVE BANK OF INDIA", "SAHEBRAO DESHMUKH COOPERATIVE BANK LIMITED", "SARASWAT COOPERATIVE BANK LIMITED", "SBER BANK", "SBM BANK MAURITIUS LIMITED", "SHIKSHAK SAHAKARI BANK LIMITED", "SHINHAN BANK", "SHRI CHHATRAPATI RAJASHRI SHAHU URBAN COOPERATIVE BANK LIMITED", "SOCIETE GENERALE", "SOLAPUR JANATA SAHAKARI BANK LIMITED", "SOUTH INDIAN BANK", "STANDARD CHARTERED BANK", "STATE BANK OF BIKANER AND JAIPUR", "STATE BANK OF HYDERABAD", "STATE BANK OF INDIA", "STATE BANK OF MAURITIUS LIMITED", "STATE BANK OF MYSORE", "STATE BANK OF PATIALA", "STATE BANK OF TRAVANCORE", "SUMITOMO MITSUI BANKING CORPORATION", "SURAT NATIONAL COOPERATIVE BANK LIMITED", "SUTEX COOPERATIVE BANK LIMITED", "SYNDICATE BANK", "TAMILNAD MERCANTILE BANK LIMITED", "THE A.P. MAHESH COOPERATIVE URBAN BANK LIMITED", "THE AKOLA DISTRICT CENTRAL COOPERATIVE BANK", "THE ANDHRA PRADESH STATE COOPERATIVE BANK LIMITED", "THE BANK OF NOVA SCOTIA", "THE COSMOS CO OPERATIVE BANK LIMITED", "THE DELHI STATE COOPERATIVE BANK LIMITED", "THE GADCHIROLI DISTRICT CENTRAL COOPERATIVE BANK LIMITED", "THE GREATER BOMBAY COOPERATIVE BANK LIMITED", "THE GUJARAT STATE COOPERATIVE BANK LIMITED", "THE HASTI COOP BANK LTD", "THE JALGAON PEOPELS COOPERATIVE BANK LIMITED", "THE KANGRA CENTRAL COOPERATIVE BANK LIMITED", "THE KANGRA COOPERATIVE BANK LIMITED", "THE KARAD URBAN COOPERATIVE BANK LIMITED", "THE KARANATAKA STATE COOPERATIVE APEX BANK LIMITED", "THE KURMANCHAL NAGAR SAHAKARI BANK LIMITED", "THE MEHSANA URBAN COOPERATIVE BANK", "THE MUMBAI DISTRICT CENTRAL COOPERATIVE BANK LIMITED", "THE MUNICIPAL COOPERATIVE BANK LIMITED", "THE NAINITAL BANK LIMITED", "THE NASIK MERCHANTS COOPERATIVE BANK LIMITED", "THE RAJASTHAN STATE COOPERATIVE BANK LIMITED", "THE ROYAL BANK OF SCOTLAND N V", "THE SEVA VIKAS COOPERATIVE BANK LIMITED", "THE SHAMRAO VITHAL COOPERATIVE BANK", "THE SURAT DISTRICT COOPERATIVE BANK LIMITED", "THE SURATH PEOPLES COOPERATIVE BANK LIMITED", "THE TAMIL NADU STATE APEX COOPERATIVE BANK", "THE THANE BHARAT SAHAKARI BANK LIMITED", "THE THANE DISTRICT CENTRAL COOPERATIVE BANK LIMITED", "THE VARACHHA COOPERATIVE BANK LIMITED", "THE VISHWESHWAR SAHAKARI BANK LIMITED", "THE WEST BENGAL STATE COOPERATIVE BANK", "THE ZOROASTRIAN COOPERATIVE BANK LIMITED", "TJSB SAHAKARI BANK LIMITED", "TJSB SAHAKARI BANK LTD", "TUMKUR GRAIN MERCHANTS COOPERATIVE BANK LIMITED", "UCO BANK", "UNION BANK OF INDIA", "UNITED BANK OF INDIA", "UNITED OVERSEAS BANK LIMITED", "VASAI VIKAS SAHAKARI BANK LIMITED", "VIJAYA BANK", "WESTPAC BANKING CORPORATION", "WOORI BANK", "YES BANK", "ZILA SAHAKRI BANK LIMITED GHAZIABAD"};
        String AXIS_BANK = "AXIS BANK";
        String HDFC_BANK = "HDFC BANK";
        String ICICI_BANK = "ICICI BANK LIMITED";
        String KOTAK_BANK = "KOTAK MAHINDRA BANK LIMITED";
        String YES_BANK = "YES BANK";
    }
}

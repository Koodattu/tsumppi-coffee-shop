package fi.cafetsumppi.app.Docs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products implements Parcelable {

    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Products(){
    }

    protected Products(Parcel in) {
        if (in.readByte() == 0x01) {
            rows = new ArrayList<Row>();
            in.readList(rows, Row.class.getClassLoader());
        } else {
            rows = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rows == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(rows);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
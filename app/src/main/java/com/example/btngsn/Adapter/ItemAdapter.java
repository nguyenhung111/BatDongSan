package com.example.btngsn.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btngsn.Activity.ManageListing;
import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Activity.UpdateListing;
import com.example.btngsn.Activity.lienhe_mua_thue;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHoler> {
    private Context context;
    ArrayList<Listing> arrayList;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int taikhoansodu;

    public ItemAdapter(Context context, ArrayList<Listing> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitemlisting, null);
        ItemHoler itemHoler = new ItemHoler(v);
        return itemHoler;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {

        final Listing listing = arrayList.get(position);

        holder.title.setText(listing.getTitle());
        holder.idListing.setText("Mã tin rao : " + listing.getIdListing());
        holder.dateStart.setText("Ngày đăng : " + listing.getDateStart());
        holder.dateEnd.setText("Ngày kết thúc : " + listing.getDateEnd());
        String trangthai = listing.getTrangthai();
        if (trangthai.equals("1")) {
            holder.status.setText("Trạng thái: Chờ xác nhận");
        } else if (trangthai.equals("2")) {
            holder.status.setText("Trạng thái: Đã đăng tin");
        } else if (trangthai.equals("3")) {
            holder.status.setText("Trạng thái: Đã hạ tin");
        }
        Glide.with(context).load(listing.getImage()).centerCrop().placeholder(R.drawable.ic_baseline_hide_image_24)
                .error(R.drawable.ic_baseline_error_24).into(holder.imageView);

        String dateStart = listing.getDateStart();
        String dateEnd = listing.getDateEnd();
        if(dateStart.equals(dateEnd)){
            String image = listing.getImage();
            image = image.substring(image.lastIndexOf("/"));
            DataClient deleteListing = APIUtils.getData();
            retrofit2.Call<String> callback = deleteListing.deleteListing(listing.getIdListing(), image);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response != null) {
                        String result = response.body();
                        Log.d("result", response.body());
                        if (result.equals("1")) {
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Các tin đến hạn đã xóa", Toast.LENGTH_SHORT).show();
                        } else if (result.equals("2")) {
                            Toast.makeText(context, "Xóa tin hết hạn thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("loi", t.getMessage());
                }
            });
        }
        String idspUser = sharedPreferences.getString("idspUser", "");
        if (idspUser.equals("1")) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Chọn chức năng bạn muốn");
                    dialog.setPositiveButton("Xóa tin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                            dialogXoa.setMessage("Bạn xác nhận xóa tin này không ?");
                            dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    String idListing = listing.getIdListing();
                                    String image = listing.getImage();
                                    image = image.substring(image.lastIndexOf("/"));
                                    Delete(idListing, image);
                                    arrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                            dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                }
                            });
                            dialogXoa.show();
                        }
                    });
                    dialog.setNegativeButton("Đăng tin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                            dialogXoa.setMessage("Bạn chắc muốn đăng tin này không ?");
                            dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    String idListing = listing.getIdListing();
                                    String idUser = listing.getIdUser();
                                    int sodu = Integer.parseInt(listing.getSodu());
                                    if (sodu >= taikhoansodu) {
                                        String trangthai = "2";
                                        UpdateAdmin(idListing, trangthai, idUser, sodu);
                                        arrayList.remove(position);
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Số dư người dùng không đủ", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                }
                            });
                            dialogXoa.show();
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        } else {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Chọn chức năng bạn muốn");
                    dialog.setPositiveButton("Xóa tin", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                            dialogXoa.setMessage("Bạn xác nhận xóa tin này không ?");
                            dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    String idListing = listing.getIdListing();
                                    String image = listing.getImage();
                                    image = image.substring(image.lastIndexOf("/"));
                                    Delete(idListing, image);
                                    arrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                            dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                }
                            });
                            dialogXoa.show();
                        }
                    });
                    int status = Integer.parseInt(listing.getTrangthai());
                    if (status == 2) {
                        dialog.setNegativeButton("Hạ tin rao", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                                dialogXoa.setMessage("Bạn xác nhận hạ tin này không ?");
                                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        String idListing = listing.getIdListing();
                                        String trangthai = "3";
                                        Update(idListing, trangthai);
                                        notifyDataSetChanged();
                                        holder.status.setText("Trạng thái: Đã hạ tin");
                                    }
                                });
                                dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                    }
                                });
                                dialogXoa.show();
                            }
                        });
                    } else if (status == 3) {
                        dialog.setNegativeButton("Đăng lại tin rao", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                                dialogXoa.setMessage("Bạn xác nhận đăng tin này không ?");
                                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        String idListing = listing.getIdListing();
                                        String trangthai = "2";
                                        Update(idListing, trangthai);
                                        holder.status.setText("Trạng thái: Đã đăng tin");
                                        notifyDataSetChanged();
                                    }
                                });
                                dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {

                                    }
                                });
                                dialogXoa.show();
                            }
                        });
                    }
                    dialog.show();
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, idListing, dateStart, dateEnd, status,sua;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);

            sharedPreferences = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            idListing = (TextView) itemView.findViewById(R.id.idListing);
            dateStart = (TextView) itemView.findViewById(R.id.dateStart);
            dateEnd = (TextView) itemView.findViewById(R.id.dateEnd);
            status = (TextView) itemView.findViewById(R.id.status);
            sua = (Button) itemView.findViewById(R.id.sua);

            sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateListing.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTitle());
                    context.startActivity(intent);
                }
            });

        }
    }

    public void Update(String idListing, String trangthai) {
        DataClient updateStatus = APIUtils.getData();
        retrofit2.Call<String> callback = updateStatus.updaeStatus(idListing, trangthai);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    int result = Integer.parseInt(response.body());
                    if (result == 1) {
                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    } else if (result == 0) {
                        Toast.makeText(context, "Thất bại mời thử lại", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }

    public void UpdateAdmin(String idListing, String trangthai, String idUser, int sodu) {
        DataClient updateStatus = APIUtils.getData();
        retrofit2.Call<String> callback = updateStatus.updaeStatus(idListing, trangthai);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    int result = Integer.parseInt(response.body());
                    if (result == 1) {
                        UpDateSodu(idUser, sodu);
                        insertTaichinh(sodu,idUser);
                    } else if (result == 0) {
                        Toast.makeText(context, "Thất bại mời thử lại", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }

    public void Delete(String idListing, String image) {
        DataClient deleteListing = APIUtils.getData();
        retrofit2.Call<String> callback = deleteListing.deleteListing(idListing, image);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String result = response.body();
                    Log.d("result", response.body());
                    if (result.equals("1")) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("2")) {
                        Toast.makeText(context, "Xóa thất bại ! mời thử lại", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }


    public void UpDateSodu(String idUser, int sodu) {
        DataClient updateStatus = APIUtils.getData();
        retrofit2.Call<String> callback = updateStatus.updateSodu(idUser, sodu);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("respone", response.body());
                if (response != null) {
                    int result = Integer.parseInt(response.body());
                    if (result == 1) {
                        Toast.makeText(context, "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                    } else if (result == 0) {
                        Toast.makeText(context, "Thất bại mời thử lại", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("loi", t.getMessage());
            }
        });
    }

    public void getData() {
        String idUser = sharedPreferences.getString("idUser", "");
        if (!idUser.equals("")) {
            DataClient loginData = APIUtils.getData();
            Call<List<User>> callback = loginData.getIdUser(idUser);
            callback.enqueue(new Callback<List<User>>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    ArrayList<User> userArrayList = (ArrayList<User>) response.body();
                    if (userArrayList.size() > 0) {
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                        taikhoansodu = Integer.parseInt(userArrayList.get(0).getSodu());
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                }
            });
        }
    }

    public void insertTaichinh(int sotien,String idUser) {

        String ngaygiaodich = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        DataClient inserData = APIUtils.getData();
        retrofit2.Call<String> callback = inserData.postTaichinh(sotien,ngaygiaodich,"1","2",idUser);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String idMuathue = response.body();
                if (idMuathue.equals("1")) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}

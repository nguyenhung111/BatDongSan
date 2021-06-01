package com.example.btngsn.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btngsn.Activity.ManageListing;
import com.example.btngsn.Activity.ProductDetail;
import com.example.btngsn.Model.CheckConnection;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.R;
import com.example.btngsn.Retrofit.APIUtils;
import com.example.btngsn.Retrofit.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHoler> {
    ManageListing context;
    ArrayList<Listing> arrayList;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ItemAdapter(ManageListing context, ArrayList<Listing> arrayList) {
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

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {

        final Listing listing = arrayList.get(position);

        holder.title.setText(listing.getTitle());
        holder.idListing.setText("Mã tin rao : " + listing.getIdListing());
        holder.dateStart.setText("Ngày đăng : " + listing.getDateStart());
        holder.dateEnd.setText("Ngày kết thúc : " + listing.getDateEnd());
        String status = listing.getTrangthai();
        if (status.equals("1")) {
            holder.status.setText("Trạng thái: " + "Chờ xác nhận");
        } else if (status.equals("2")) {
            holder.status.setText("Trạng thái: " + "Đã đăng tin");
        } else {
            holder.status.setText("Trạng thái: " + "Đã hạ tin");
        }
        Glide.with(context).load(listing.getImage()).centerCrop().placeholder(R.drawable.ic_baseline_hide_image_24)
                .error(R.drawable.ic_baseline_error_24).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHoler extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, idListing, dateStart, dateEnd, status;

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra("thongtinchitiet", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast(context, arrayList.get(getPosition()).getTitle());
                    context.startActivity(intent);
                }
            });
            String idspUser = sharedPreferences.getString("idspUser", "");
            if (idspUser.equals("1")) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
                                        String idListing = arrayList.get(getPosition()).getIdListing();
                                        String image = arrayList.get(getPosition()).getImage();
                                        image = image.substring(image.lastIndexOf("/"));
                                        Log.d("image", image);
                                        Log.d("image", idListing);
                                        Delete(idListing, image);
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
                                        String idListing = arrayList.get(getPosition()).getIdListing();
                                        String trangthai = "2";
                                        Log.d("bb", String.valueOf(getPosition()));
                                        UpdateAdmin(idListing, trangthai, getPosition());
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
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
                                        String idListing = arrayList.get(getPosition()).getIdListing();
                                        String image = arrayList.get(getPosition()).getImage();
                                        image = image.substring(image.lastIndexOf("/"));
                                        Delete(idListing, image);
                                        arrayList.remove(getPosition());
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
                        int status = Integer.parseInt(arrayList.get(getPosition()).getTrangthai());
                        if (status == 2) {
                            dialog.setNegativeButton("Hạ tin rao", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
                                    dialogXoa.setMessage("Bạn xác nhận hạ tin này không ?");
                                    dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            String idListing = arrayList.get(getPosition()).getIdListing();
                                            String trangthai = "3";
                                            Update(idListing, trangthai);
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
                                    dialogXoa.setMessage("Bạn xác nhận đăngtin này không ?");
                                    dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            String idListing = arrayList.get(getPosition()).getIdListing();
                                            String trangthai = "2";
                                            Update(idListing, trangthai);
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

    public void UpdateAdmin(String idListing, String trangthai, int position) {
        DataClient updateStatus = APIUtils.getData();
        retrofit2.Call<String> callback = updateStatus.updaeStatus(idListing, trangthai);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    int result = Integer.parseInt(response.body());
                    if (result == 1) {
                        arrayList.remove(position);
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

}

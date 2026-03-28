package com.example.cityguide.ui.attractions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cityguide.MainActivity;
import com.example.cityguide.R;
import com.example.cityguide.data.models.Attraction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddEditAttractionFragment extends Fragment {

    private AttractionViewModel viewModel;
    private EditText etName, etCity, etAddress, etLatitude, etLongitude;
    private ImageView ivAttractionImage;
    private Button btnSave;
    private ImageButton btnBack;

    private Attraction currentAttraction;
    private int attractionId = -1;
    private Uri imageUri;
    private String imagePath;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(getContext(), "Нужно разрешение для доступа к фото", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_attraction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация элементов
        btnBack = view.findViewById(R.id.btnBack);
        etName = view.findViewById(R.id.etName);
        etCity = view.findViewById(R.id.etCity);
        etAddress = view.findViewById(R.id.etAddress);
        etLatitude = view.findViewById(R.id.etLatitude);
        etLongitude = view.findViewById(R.id.etLongitude);
        ivAttractionImage = view.findViewById(R.id.ivAttractionImage);
        btnSave = view.findViewById(R.id.btnSave);

        // 🔹 Кнопка "Назад"
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> goBack());
        }

        // Получение ID достопримечательности (если есть)
        if (getArguments() != null) {
            attractionId = getArguments().getInt("attraction_id", -1);
        }

        // Инициализация ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(AttractionViewModel.class);

        // Если редактирование - загрузить данные
        if (attractionId != -1) {
            viewModel.getAllAttractions().observe(getViewLifecycleOwner(), attractions -> {
                for (Attraction attraction : attractions) {
                    if (attraction.getId() == attractionId) {
                        currentAttraction = attraction;
                        loadData(attraction);
                        break;
                    }
                }
            });
        }

        // Клик по изображению
        ivAttractionImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        // Кнопка сохранения
        btnSave.setOnClickListener(v -> saveAttraction());
    }

    // 🔹 Метод возврата назад
    private void goBack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void loadData(Attraction attraction) {
        etName.setText(attraction.getName());
        etCity.setText(attraction.getCity());
        etAddress.setText(attraction.getAddress());
        etLatitude.setText(String.valueOf(attraction.getLatitude()));
        etLongitude.setText(String.valueOf(attraction.getLongitude()));

        if (attraction.getImagePath() != null && !attraction.getImagePath().isEmpty()) {
            File imageFile = new File(attraction.getImagePath());
            if (imageFile.exists()) {
                Glide.with(this).load(imageFile).into(ivAttractionImage);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == requireActivity().RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    ivAttractionImage.setImageURI(imageUri);
                    saveImageToInternalStorage();
                }
            });

    private void saveImageToInternalStorage() {
        if (imageUri == null) return;

        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            File file = new File(requireContext().getFilesDir(), "attraction_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            imagePath = file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка сохранения фото", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAttraction() {
        String name = etName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String latStr = etLatitude.getText().toString().trim();
        String lngStr = etLongitude.getText().toString().trim();

        if (name.isEmpty() || city.isEmpty() || address.isEmpty() || latStr.isEmpty() || lngStr.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latStr);
            longitude = Double.parseDouble(lngStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Неверный формат координат", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentAttraction == null) {
            // Создание новой
            currentAttraction = new Attraction(name, city, address, latitude, longitude, imagePath);
            viewModel.insert(currentAttraction);
            Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
        } else {
            // Обновление
            currentAttraction.setName(name);
            currentAttraction.setCity(city);
            currentAttraction.setAddress(address);
            currentAttraction.setLatitude(latitude);
            currentAttraction.setLongitude(longitude);
            if (imagePath != null) {
                currentAttraction.setImagePath(imagePath);
            }
            viewModel.update(currentAttraction);
            Toast.makeText(getContext(), "Обновлено", Toast.LENGTH_SHORT).show();
        }

        // 🔹 Возврат назад вместо Navigation
        goBack();
    }
}
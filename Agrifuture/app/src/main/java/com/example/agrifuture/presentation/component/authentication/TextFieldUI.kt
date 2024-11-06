package com.example.agrifuture.presentation.component.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.agrifuture.R

@Composable
fun TextFieldUI(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIconId: Int,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityToggle: (() -> Unit)? = null,
    errorMessage: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconId),
                contentDescription = label,
                modifier = Modifier.size(30.dp)
            )
        },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onPasswordVisibilityToggle?.invoke() }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible)
                                R.drawable.on_visibility
                            else
                                R.drawable.off_visibility
                        ),
                        contentDescription = if (isPasswordVisible)
                            "Hide password"
                        else
                            "Show password"
                    )
                }
            }
        } else null,
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = if (!errorMessage.isNullOrEmpty()) {
            { Text(text = errorMessage) }
        } else {
            null
        }
    )
}

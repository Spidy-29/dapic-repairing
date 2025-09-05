package com.dapic.mobile.repairing.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dapic.mobile.repairing.FormViewModel
import com.dapic.mobile.repairing.ui.screen.EmployeeAuthFormScreen
import com.dapic.mobile.repairing.ui.screen.EndUserNameFormScreen
import com.dapic.mobile.repairing.ui.screen.SplashScreen
import com.dapic.mobile.repairing.ui.screen.CallTypeFormScreen
import com.dapic.mobile.repairing.ui.screen.CurrentStatusFormScreen
import com.dapic.mobile.repairing.ui.screen.DateFormScreen
import com.dapic.mobile.repairing.ui.screen.OEMNameFormScreen
import com.dapic.mobile.repairing.ui.screen.PanelCodeFormScreen
import com.dapic.mobile.repairing.ui.screen.RemarkFormScreen
import com.dapic.mobile.repairing.ui.screen.ServiceReqNumberFormScreen
import com.dapic.mobile.repairing.ui.screen.TABillFormScreen
import com.dapic.mobile.repairing.ui.screen.WorkAllocationFormScreen

const val SPLASH_SCREEN = "SPLASH_SCREEN"

//const val AUTH_SCREEN = "AUTH_SCREEN"
const val EMPLOYEE_AUTH_SCREEN = "EMPLOYEE_AUTH_SCREEN"
const val DATE_SCREEN = "DATE_SCREEN"
const val WORK_ALLOCATION_SCREEN = "WORK_ALLOCATION_SCREEN"
const val OEM_NAME_SCREEN = "OEM_NAME_SCREEN"
const val CALL_TYPE_SCREEN = "CALL_TYPE_SCREEN"
const val END_USER_SCREEN = "END_USER_SCREEN"
const val PANEL_CODE_SCREEN = "PANEL_CODE_SCREEN"
const val SERVICE_REQUEST_SCREEN = "SERVICE_REQUEST_SCREEN"
const val CURRENT_STATUS_SCREEN = "CURRENT_STATUS_SCREEN"
const val TA_BILL_SCREEN = "TA_BILL_SCREEN"
const val REMARK_SCREEN = "REMARK_SCREEN"

@Composable
fun MainNavigation(viewModel: FormViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen {
                navController.navigate(EMPLOYEE_AUTH_SCREEN) {
                    popUpTo(SPLASH_SCREEN) {
                        inclusive = true
                    } // Remove SplashScreen from backstack
                }
            }
        }
        // Employee Code Form Screen
        composable(EMPLOYEE_AUTH_SCREEN) {
            EmployeeAuthFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(DATE_SCREEN)
            })
        }

        // Date Form Screen
        composable(
            route = DATE_SCREEN,
        ) { backStackEntry ->
            DateFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(WORK_ALLOCATION_SCREEN)
            })
        }

        // WorkAllocation Screen
        composable(
            route = WORK_ALLOCATION_SCREEN,
        ) { backStackEntry ->
            WorkAllocationFormScreen(viewModel = viewModel, onContinue = {
                val formState = viewModel.formState.value

                // Determine whether to show the Submit button
                val canSubmit = when {
                    formState.workAllocationType == "Office" && formState.workAllocation == "None" -> true
                    formState.workAllocationType == "WFH" && formState.workAllocation == "None" -> true
                    formState.workAllocationType == "Site Visit" && formState.workAllocation == "Exhibition" -> true
                    formState.workAllocationType in listOf(
                        "AB", "PH", "Travelling", "Exhibition"
                    ) -> true

                    else -> false
                }

                val navigateScreen = when {
                    formState.workAllocationType == "Office" && formState.workAllocation in listOf(
                        "Other Development", "Program Development"
                    ) -> CURRENT_STATUS_SCREEN

                    formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call" -> OEM_NAME_SCREEN

                    canSubmit -> REMARK_SCREEN

                    else -> CALL_TYPE_SCREEN
                }

                navController.navigate(navigateScreen)
            })
        }

        // OEM Name Screen
        composable(
            route = OEM_NAME_SCREEN,
        ) { backStackEntry ->
            OEMNameFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(END_USER_SCREEN)
            })
        }

        composable(
            route = CALL_TYPE_SCREEN,
        ) { backStackEntry ->
            CallTypeFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(OEM_NAME_SCREEN)
            })
        }

        composable(
            route = END_USER_SCREEN,
        ) { backStackEntry ->
            EndUserNameFormScreen(viewModel = viewModel, onContinue = {
                val formState = viewModel.formState.value
                if (formState.workAllocationType == "Site Visit" && formState.workAllocation == "Sales Call") {
                    navController.navigate(REMARK_SCREEN)
                } else navController.navigate(PANEL_CODE_SCREEN)

            })
        }

        composable(
            route = PANEL_CODE_SCREEN,
        ) { backStackEntry ->
            PanelCodeFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(SERVICE_REQUEST_SCREEN)
            })
        }

        composable(
            route = SERVICE_REQUEST_SCREEN,
        ) { backStackEntry ->
            ServiceReqNumberFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(CURRENT_STATUS_SCREEN)
            })
        }

        composable(
            route = CURRENT_STATUS_SCREEN,
        ) { backStackEntry ->
            CurrentStatusFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(REMARK_SCREEN)
            })
        }

        composable(
            route = TA_BILL_SCREEN,
        ) { backStackEntry ->
            TABillFormScreen(viewModel = viewModel, onContinue = {
                navController.navigate(REMARK_SCREEN)
            })
        }

        composable(
            route = REMARK_SCREEN,
        ) { backStackEntry ->
            RemarkFormScreen(viewModel = viewModel, onSubmit = {
                viewModel.clearValues()
                navController.navigate(EMPLOYEE_AUTH_SCREEN) {
                    popUpTo(EMPLOYEE_AUTH_SCREEN) {
                        inclusive = false
                    } // Remove SplashScreen from backstack
                }
            })
        }
    }
}
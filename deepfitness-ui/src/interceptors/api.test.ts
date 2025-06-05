import MockAdapter from "axios-mock-adapter";
import axiosHttp from "./verifyAuth";
import {
  verifyUser,
  registerActiviy,
  getUserActivities,
  getUserRecommandation,
  removeUserActivity,
} from "./api";

describe("api module", () => {
  let mock: MockAdapter;

  beforeEach(() => {
    mock = new MockAdapter(axiosHttp);
    localStorage.clear();
  });

  afterEach(() => {
    mock.restore();
  });

  it("verifyUser should call /users/validate/:userId", async () => {
    localStorage.setItem("userId", "user-123");
    mock.onGet("/users/validate/user-123").reply(200, { valid: true });

    const response = await verifyUser(); // <-- call as a function!
    const data = await verifyUser().then(res => res.data);
    expect(response.status).toBe(200);
    expect(data).toEqual({ valid: true });
  });

  it("registerActiviy should POST to /activity/register-user-activity", async () => {
    const payload = { foo: "bar" };
    mock.onPost("/activity/register-user-activity", payload).reply(201, { success: true });

    const response = await registerActiviy(payload);
    expect(response.status).toBe(201);
    expect(response.data).toEqual({ success: true });
  });

  it("getUserActivities should GET /activity/user-activities with X-USER-ID header", async () => {
    mock.onGet("/activity/user-activities").reply((config) => {
      // Axios may lowercase header keys or nest them under common
      const headers =
        config.headers?.common ||
        config.headers ||
        {};
      const userIdHeader =
        headers["X-USER-ID"] ||
        headers["x-user-id"] ||
        (typeof headers.get === "function" && headers.get("X-USER-ID")) ||
        (typeof headers.get === "function" && headers.get("x-user-id"));
      expect(userIdHeader).toBe("user-123");
      return [200, [{ id: 1 }]];
    });

    const response = await getUserActivities("user-123");
    expect(response.status).toBe(200);
    expect(response.data).toEqual([{ id: 1 }]);
  });

  it("getUserRecommandation should GET ai/recommendations/activity/:activityId", async () => {
    mock.onGet("/ai/recommendations/activity/act-42").reply(200, { rec: "do this" });

    const response = await getUserRecommandation("act-42");
    expect(response.status).toBe(200);
    expect(response.data).toEqual({ rec: "do this" });
  });

  it("removeUserActivity should DELETE /activity/:activityId", async () => {
    mock.onDelete("/activity/act-99").reply(204);

    const response = await removeUserActivity("act-99");
    expect(response.status).toBe(204);
  });
});

export const localVerifyUser = () => {
  const userId = localStorage.getItem('userId');
  return axiosHttp.get(`/users/validate/${userId}`);
};